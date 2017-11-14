package com.bulingzhuang.gentcomic.impl.presenters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import com.bulingzhuang.gentcomic.adapters.MainListAdapter
import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.entity.WeatherData
import com.bulingzhuang.gentcomic.impl.interactors.MainInteractorImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.MainPresenter
import com.bulingzhuang.gentcomic.interfaces.views.MainView
import com.bulingzhuang.gentcomic.utils.Constants
import com.bulingzhuang.gentcomic.utils.SharePreferencesUtil
import com.bulingzhuang.gentcomic.utils.net.ApiCallback
import com.bulingzhuang.gentcomic.utils.net.ApiCallbackWithPage
import com.bulingzhuang.gentcomic.utils.showLogE
import com.google.gson.Gson

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/9
 * 描    述：
 * ================================================
 */
class MainPresenterImpl(private val mView: MainView) : MainPresenter {

    private val mInteractor = MainInteractorImpl()
    private lateinit var mAdapter: MainListAdapter
    private var mLastPageNum = 1

    private var mLastVisibleItem = 0
    /**
     * 初始化Adapter
     */
    override fun initAdapter(context: AppCompatActivity, recyclerView: RecyclerView) {
        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position < 1) {
                    3
                } else {
                    1
                }
            }
        }
        recyclerView.layoutManager = layoutManager
        val dm = context.resources.displayMetrics
//        showLogE("屏幕height=${dm.heightPixels}，width=${dm.widthPixels}，宽高比=${dm.heightPixels.toFloat()/dm.widthPixels}")
        val isFullScreen = dm.heightPixels.toFloat()/dm.widthPixels >1.78f
        mAdapter = MainListAdapter(context,isFullScreen)
        recyclerView.adapter = mAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 1 == mAdapter.itemCount) {
                    getMainListData(context, false)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mLastVisibleItem = (recyclerView?.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
        })
    }

    //修改前默认天气城市
    private var mLastWeatherCity: String = SharePreferencesUtil.getString(Constants.SP_WEATHER_CITY)

    /**
     * 获取天气数据
     */
    override fun getWeatherData(context: Context, city: String) {
        val lastRefreshDataStr = SharePreferencesUtil.getString(Constants.SP_MAIN_LAST_WEATHER_REFRESH_DATA)
        showLogE("上次一访问天气接口数据：$lastRefreshDataStr")
        var refresh = true
        val gson = Gson()
        if (lastRefreshDataStr.isNotEmpty()) {
            val data = gson.fromJson(lastRefreshDataStr, WeatherData.ResultsBean.NowBean::class.java)
            if (System.currentTimeMillis() - data.createTime < 1000 * 60 * 30) {
                //要调用缓存天气数据时，校验是否修改了城市，是则继续走net刷新
                val currentCity = SharePreferencesUtil.getString(Constants.SP_WEATHER_CITY)
                if (mLastWeatherCity == currentCity) {
                    showLogE("使用本地缓存的数据")
                    refresh = false
                    mView.updateWeather(data, false)
                } else {
                    mLastWeatherCity = currentCity
                }
            }
        }
        if (refresh) {
            mInteractor.requestWeatherData(object : ApiCallback<WeatherData>() {
                override fun onFailure(msg: String?) {
                    mView.updateError(msg)
                }

                override fun onFinish() {

                }

                override fun onSuccess(module: WeatherData) {
                    val data = module.results[0].now
                    mView.updateWeather(data)
                    data.createTime = System.currentTimeMillis()
                    val jsonData = gson.toJson(data)
                    showLogE("保存了天气数据：$jsonData")
                    SharePreferencesUtil.setValue(context, Constants.SP_MAIN_LAST_WEATHER_REFRESH_DATA, jsonData)
                }

            }, city)
        }
    }

    /**
     * 获取主页列表数据
     */
    override fun getMainListData(context: Context, isRefresh: Boolean) {
        if (isRefresh) {
            mLastPageNum = 1
        } else {
            ++mLastPageNum
        }
        showLogE("加载了第$mLastPageNum 页")
        mInteractor.requestMainListData(object : ApiCallbackWithPage<MainListData>(mLastPageNum) {
            override fun onSuccess(module: MainListData, pageNum: Int) {
                mLastPageNum = pageNum
                if (mLastPageNum <= 1) {
                    mAdapter.addAll(module.result)
                } else {
                    mAdapter.addAll(module.result, false)
                }
            }

            override fun onFailure(msg: String?) {
                mView.updateError(msg)
            }

            override fun onFinish() {
                mView.setRefreshing(false)
            }

        })
    }
}