package com.bulingzhuang.gentcomic.impl.presenters

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.bulingzhuang.gentcomic.adapters.ComicIndexAdapter
import com.bulingzhuang.gentcomic.entity.ComicIndexData
import com.bulingzhuang.gentcomic.entity.ComicReadEntity
import com.bulingzhuang.gentcomic.impl.interactors.ComicIndexInteractorImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.ComicIndexPresenter
import com.bulingzhuang.gentcomic.interfaces.views.ComicIndexView
import com.bulingzhuang.gentcomic.utils.database
import com.bulingzhuang.gentcomic.utils.db.ComicReadRowParser
import com.bulingzhuang.gentcomic.utils.db.DBUtil
import com.bulingzhuang.gentcomic.utils.net.ApiCallback
import com.bulingzhuang.gentcomic.utils.showLogE
import org.jetbrains.anko.db.select

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
    override fun initAdapter(context: Context, recyclerView: RecyclerView, title: String, comicID: String) {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        mAdapter = ComicIndexAdapter(context, title, comicID)
        recyclerView.adapter = mAdapter
    }

    /**
     * 获取漫画列表数据
     */
    override fun getComicIndexData(context: Context, comicID: String) {
        mView.showRefreshing()
        mInteractor.requestComicIndexData(comicID, object : ApiCallback<ComicIndexData>() {
            override fun onSuccess(module: ComicIndexData) {
                context.database.use {
                    val select = select(DBUtil.TABLE_read).whereSimple("${DBUtil.READ_comicID} = ?", comicID)
                    val dataList = ArrayList<ComicReadEntity>(select.parseList(ComicReadRowParser()))
                    item@ for (item in module.result) {
                        childItem@ for (childItem in dataList) {
                            if (item.volsID == childItem.volsID) {
                                //表示已读过
                                item.hasRead = true
                                if (dataList.remove(childItem)) {
                                    if (dataList.size > 0) {
                                        break@childItem
                                    } else {
                                        break@item
                                    }
                                }
                            }
                        }
                    }
                }
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

    /**
     * 销毁操作
     */
    override fun onDestroy() {
        mInteractor.doBeforeDestroy()
    }
}