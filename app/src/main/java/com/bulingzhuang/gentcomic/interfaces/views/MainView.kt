package com.bulingzhuang.gentcomic.interfaces.views

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/9
 * 描    述：
 * ================================================
 */
interface MainView {

    /**
     * 返回错误信息
     */
    fun updateError(eText:String?)

    /**
     * 结束刷新
     */
    fun setRefreshing(refreshing:Boolean)
}