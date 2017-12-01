package com.bulingzhuang.gentcomic.interfaces.presenters

import android.content.Context
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/9
 * 描    述：
 * ================================================
 */
interface MainHomePresenter {

    /**
     * 初始化Adapter
     */
    fun initAdapter(context: FragmentActivity, recyclerView: RecyclerView)

    /**
     * 获取天气数据
     */
    fun getWeatherData(context: Context, @Nullable city: String)

    /**
     * 获取首页列表数据
     */
    fun getMainHomeListData(context: Context, isRefresh: Boolean = true)

    /**
     * 销毁操作
     */
    fun onDestroy()
}