package com.bulingzhuang.gentcomic.base

import android.support.v4.app.Fragment

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/1
 * 描    述：
 * ================================================
 */
open class BaseFragment : Fragment() {
    companion object {
        val TITLE = "com.bulingzhuang.gentcomic.titleName"
    }
    var title = ""
}