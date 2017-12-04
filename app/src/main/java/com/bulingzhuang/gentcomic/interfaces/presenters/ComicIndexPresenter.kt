package com.bulingzhuang.gentcomic.interfaces.presenters

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/14
 * 描    述：
 * ================================================
 */
interface ComicIndexPresenter {

    /**
     * 初始化Adapter
     */
    fun initAdapter(context: Context, recyclerView: RecyclerView, title: String, comicID: String)

    /**
     * 检查是否已收藏、已下载
     */
    fun checkStatus(context: Context, comicID: String)

    /**
     * 点击start按钮
     */
    fun clickStar(context: Context)

    /**
     * 点击download按钮
     */
    fun clickDownload(context: Context)

    /**
     * 获取漫画目录数据
     */
    fun getComicIndexData(context: Context, comicID: String)

    /**
     * 销毁操作
     */
    fun onDestroy()
}