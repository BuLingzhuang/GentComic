package com.bulingzhuang.gentcomic.base

import android.app.Application
import com.bulingzhuang.gentcomic.utils.SharePreferencesUtil

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/13
 * 描    述：
 * ================================================
 */
class GentComicApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化SharePreference
        SharePreferencesUtil.initializeInstance(this)
    }
}