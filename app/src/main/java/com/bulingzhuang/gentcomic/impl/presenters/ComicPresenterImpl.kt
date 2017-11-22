package com.bulingzhuang.gentcomic.impl.presenters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.widget.TextView
import com.bulingzhuang.gentcomic.interfaces.presenters.ComicPresenter
import com.bulingzhuang.gentcomic.receiver.ComicReceiver
import java.util.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/22
 * 描    述：
 * ================================================
 */
class ComicPresenterImpl:ComicPresenter {

    private lateinit var mComicReceiver: ComicReceiver

    /**
     * 初始化各类状态监听
     */
    @SuppressLint("SetTextI18n")
    override fun initStatusReceiver(context: Context,tvBattery: TextView, tvTime: TextView, tvNet: TextView) {
        val calendar = Calendar.getInstance()
        tvTime.text = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

        mComicReceiver = ComicReceiver(tvBattery, tvTime, tvNet)
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_CHANGED)
        filter.addAction(Intent.ACTION_TIME_TICK)
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(mComicReceiver, filter)
    }

    /**
     * 销毁操作
     */
    override fun onDestroy(context: Context) {
        context.unregisterReceiver(mComicReceiver)
    }
}