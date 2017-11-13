package com.bulingzhuang.gentcomic.interfaces.presenters

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/9
 * 描    述：
 * ================================================
 */
interface MainPresenter {

    /**
     * 初始化Adapter
     */
    fun initAdapter(context: Context, recyclerView: RecyclerView)

    /**
     * 获取首页列表内容
     */
    fun getMainListData(context: Context, isRefresh: Boolean = true)
}