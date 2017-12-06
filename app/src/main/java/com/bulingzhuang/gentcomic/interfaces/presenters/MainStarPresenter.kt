package com.bulingzhuang.gentcomic.interfaces.presenters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.widget.ImageView

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/4
 * 描    述：
 * ================================================
 */
interface MainStarPresenter {

    /**
     * 初始化收藏列表
     */
    fun initStarAdapter(recyclerView: RecyclerView)

    /**
     * 刷新收藏列表
     */
    fun refreshStarList()
}