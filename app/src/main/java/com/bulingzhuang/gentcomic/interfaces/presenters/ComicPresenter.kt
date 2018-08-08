package com.bulingzhuang.gentcomic.interfaces.presenters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/22
 * 描    述：
 * ================================================
 */
interface ComicPresenter {
    /**
     * 初始化各类状态监听
     */
    fun init(context: Context, tvBattery: TextView, ivCharging: ImageView, tvTime: TextView, tvNet: TextView, rvContent: RecyclerView)

    /**
     * 获取漫画数据
     */
    fun getComicData(context: Context,volID:String)

    /**
     * 销毁操作
     */
    fun onDestroy(context: Context)
}