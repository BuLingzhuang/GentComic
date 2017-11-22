package com.bulingzhuang.gentcomic.impl.presenters

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.bulingzhuang.gentcomic.adapters.ComicIndexAdapter
import com.bulingzhuang.gentcomic.entity.ComicIndexData
import com.bulingzhuang.gentcomic.impl.interactors.ComicIndexInteractorImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.ComicIndexPresenter
import com.bulingzhuang.gentcomic.interfaces.views.ComicIndexView
import com.bulingzhuang.gentcomic.utils.net.ApiCallback

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/14
 * 描    述：
 * ================================================
 */
class ComicIndexPresenterImpl(private val mView: ComicIndexView) : ComicIndexPresenter {

    private val mInteractor = ComicIndexInteractorImpl()
    private lateinit var mAdapter: ComicIndexAdapter

    /**
     * 初始化Adapter
     */
    override fun initAdapter(context: Context, recyclerView: RecyclerView, title: String) {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        mAdapter = ComicIndexAdapter(context,title)
        recyclerView.adapter = mAdapter
    }

    /**
     * 获取漫画列表数据
     */
    override fun getComicIndexData(context: Context, comicID: String) {
        mView.showRefreshing()
        mInteractor.requestComicIndexData(comicID, object : ApiCallback<ComicIndexData>() {
            override fun onSuccess(module: ComicIndexData) {
                mAdapter.addAll(module.result)
            }

            override fun onFailure(msg: String?) {
                mView.showSnakeBar(msg.let { it } ?: "请求失败")
            }

            override fun onFinish() {
                mView.showRefreshing(false)
            }
        })
    }
}