package com.bulingzhuang.gentcomic.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.widget.TextView
import com.bulingzhuang.gentcomic.utils.NetWorkUtils
import com.bulingzhuang.gentcomic.utils.showLogE
import java.util.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/22
 * 描    述：电量监听广播
 * ================================================
 */
class ComicReceiver(private val tvBattery: TextView, private val tvTime: TextView, private val tvNet: TextView) : BroadcastReceiver() {
    @SuppressLint("SetTextI18n")
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BATTERY_CHANGED -> {
                val current = intent.extras.getInt("level")//当前电量
                val total = intent.extras.getInt("scale")//总电量
                val percent = current * 100 / total
                tvBattery.text = "电量:$percent%"
            }
            Intent.ACTION_TIME_TICK -> {
                val calendar = Calendar.getInstance()
                tvTime.text = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
            }
        // 监听wifi的打开与关闭，与wifi的连接无关 WifiManager.WIFI_STATE_CHANGED_ACTION
        // 监听wifi的连接状态即是否连上了一个有效无线路由 WifiManager.NETWORK_STATE_CHANGED_ACTION
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                tvNet.text = if (context != null) {
                    //没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
                    val apnType = NetWorkUtils.getAPNType(context)
                    when (apnType) {
                        0 -> "无网络"
                        1 -> "WIFI"
                        2 -> "2G"
                        3 -> "3G"
                        4 -> "4G"
                        else -> "网络检测异常"
                    }
                } else {
                    "网络检测异常"
                }
            }
        }
    }
}