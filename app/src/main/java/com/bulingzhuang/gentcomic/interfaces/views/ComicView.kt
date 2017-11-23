package com.bulingzhuang.gentcomic.interfaces.views

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/14
 * 描    述：
 * ================================================
 */
interface ComicView {
    /**
     * 返回错误信息
     */
    fun showSnakeBar(msg:String)

    /**
     * 显示/隐藏刷新
     */
    fun showRefreshing(show:Boolean = true)
}