package com.bulingzhuang.gentcomic.impl.presenters

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bulingzhuang.gentcomic.adapters.MainListAdapter
import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.impl.interactors.MainInteractorImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.MainPresenter
import com.bulingzhuang.gentcomic.interfaces.views.MainView
import com.bulingzhuang.gentcomic.utils.net.ApiCallbackWithPage
import com.bulingzhuang.gentcomic.utils.showLogE

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
    override fun initAdapter(context: Context, recyclerView: RecyclerView) {
        val layoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = layoutManager
        mAdapter = MainListAdapter(context)
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