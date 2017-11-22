package com.bulingzhuang.gentcomic.interfaces.presenters

import android.content.Context
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
    fun initStatusReceiver(context: Context,tvBattery: TextView, tvTime: TextView, tvNet: TextView)

    /**
     * 销毁操作
     */
    fun onDestroy(context: Context)
}