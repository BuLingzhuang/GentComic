package com.bulingzhuang.gentcomic.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.entity.WeatherData
import com.bulingzhuang.gentcomic.impl.presenters.MainPresenterImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.MainPresenter
import com.bulingzhuang.gentcomic.interfaces.views.MainView
import com.bulingzhuang.gentcomic.utils.showSnakeBar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 版    本：1.0
 * 创建日期：2017/11/8
 * 描    述：首页
 * 修订历史：
 * ================================================
 */
class MainActivity : AppCompatActivity(), MainView {

    private lateinit var mPresenter:MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        mPresenter.getMainListData(this)
    }

    private fun init() {
        mPresenter = MainPresenterImpl(this)
        mPresenter.initAdapter(this,rv_content)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    tv_title.setText(R.string.title_home)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_star -> {
                    tv_title.setText(R.string.title_star)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_download -> {
                    tv_title.setText(R.string.title_download)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_mine -> {
                    tv_title.setText(R.string.title_mine)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        srl_content.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        srl_content.setOnRefreshListener {
            mPresenter.getMainListData(this)
        }
    }

    /**
     * 更新天气信息
     */
    override fun updateWeather(data: WeatherData.ResultsBean.NowBean, showAnim: Boolean) {
//        val temp = data.temperature
//        val code = data.code
//        if (temp.startsWith("-")) {
//            temp_weather.refreshTempData(temp.substring(1, temp.length).toInt(), showAnim, true)
//        } else {
//            temp_weather.refreshTempData(temp.toInt(), showAnim)
//        }
//        when (code) {
//            "4", "5", "6", "7", "8", "9", "30", "31", "34", "35", "36" -> {
//                iv_weather.setImageResource(R.drawable.ic_cloud)
//            }
//            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25" -> {
//                iv_weather.setImageResource(R.drawable.ic_rain)
//            }
//            else -> {
//                iv_weather.setImageResource(R.drawable.ic_sun)
//            }
//        }
    }

    override fun showSnakeBar(msg: String) {
        showSnakeBar(msg, cl_gen)
    }

    override fun setRefreshing(refreshing: Boolean) {
        srl_content.isRefreshing = refreshing
    }
}
