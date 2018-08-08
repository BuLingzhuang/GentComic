package com.bulingzhuang.gentcomic.impl.presenters

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.bulingzhuang.gentcomic.adapters.ComicIndexAdapter
import com.bulingzhuang.gentcomic.entity.ComicIndexData
import com.bulingzhuang.gentcomic.entity.ComicReadEntity
import com.bulingzhuang.gentcomic.entity.ComicStatusEntity
import com.bulingzhuang.gentcomic.impl.interactors.ComicIndexInteractorImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.ComicIndexPresenter
import com.bulingzhuang.gentcomic.interfaces.views.ComicIndexView
import com.bulingzhuang.gentcomic.utils.database
import com.bulingzhuang.gentcomic.utils.db.ComicReadRowParser
import com.bulingzhuang.gentcomic.utils.db.ComicStatusRowParser
import com.bulingzhuang.gentcomic.utils.db.DBUtil
import com.bulingzhuang.gentcomic.utils.net.ApiCallback
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

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
    private lateinit var mStatus: ComicStatusEntity
    private lateinit var mTitle: String
    private lateinit var mImageUrl: String

    /**
     * 初始化Adapter
     */
    override fun initAdapter(context: Context, recyclerView: RecyclerView, title: String, comicID: String) {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        mAdapter = ComicIndexAdapter(context, title, comicID)
        recyclerView.adapter = mAdapter
    }

    /**
     * 检查是否已收藏、已下载
     */
    override fun checkStatus(context: Context, title: String, imageUrl: String, comicID: String) {
        mTitle = title
        mImageUrl = imageUrl
        context.database.use {
            val select = select(DBUtil.TABLE_status).whereSimple("${DBUtil.STATUS_comicID} = ?", comicID)
            val dataList = ArrayList<ComicStatusEntity>(select.parseList(ComicStatusRowParser()))
            mStatus = if (dataList.size <= 0) {
                val timeMillis = System.currentTimeMillis()
                insert(DBUtil.TABLE_status, DBUtil.STATUS_comicID to comicID,
                        DBUtil.STATUS_title to mTitle, DBUtil.STATUS_imageUrl to mImageUrl,
                        DBUtil.STATUS_isStar to "0", DBUtil.STATUS_isDownload to "0",
                        DBUtil.STATUS_createTime to timeMillis)
                ComicStatusEntity(comicID, title, imageUrl, false, false,timeMillis)
            } else {
                dataList[0]
            }
            mView.refreshStatus(mStatus.isStar, mStatus.isDownload)
        }
    }

    /**
     * 点击start按钮
     */
    override fun clickStar(context: Context) {
        val currentStar = !mStatus.isStar
        context.database.use {
            update(DBUtil.TABLE_status,
                    DBUtil.STATUS_comicID to mStatus.comicID,
                    DBUtil.STATUS_title to mTitle,
                    DBUtil.STATUS_imageUrl to mImageUrl,
                    DBUtil.STATUS_isStar to if (currentStar) "1" else "0",
                    DBUtil.STATUS_isDownload to mStatus.isDownload,
                    DBUtil.STATUS_createTime to mStatus.createTime)
                    .whereArgs("${DBUtil.STATUS_comicID} = {${DBUtil.STATUS_comicID}}", DBUtil.STATUS_comicID to mStatus.comicID).exec()
        }
        mStatus.isStar = currentStar
        mView.refreshStatus(mStatus.isStar, mStatus.isDownload)
    }

    /**
     * 点击download按钮
     */
    override fun clickDownload(context: Context) {}

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
                    item@ for (item in module.data) {
                        childItem@ for (childItem in dataList) {
                            if (item.volID == childItem.volID) {
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
                mAdapter.addAll(module.data)
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