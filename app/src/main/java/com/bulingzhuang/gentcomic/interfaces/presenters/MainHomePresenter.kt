package com.bulingzhuang.gentcomic.interfaces.presenters

import android.support.annotation.Nullable
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
    fun initAdapter(recyclerView: RecyclerView)

    /**
     * 获取天气数据
     */
    fun getWeatherData(@Nullable city: String)

    /**
     * 获取首页列表数据
     */
    fun getMainHomeListData(isRefresh: Boolean = true)

    /**
     * 只刷新本地收藏标记
     */
    fun refreshStar()

    /**
     * 销毁操作
     */
    fun onDestroy()
}