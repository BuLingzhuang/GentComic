package com.bulingzhuang.gentcomic.impl.presenters

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import com.bulingzhuang.gentcomic.adapters.MainStarAdapter
import com.bulingzhuang.gentcomic.entity.ComicStatusEntity
import com.bulingzhuang.gentcomic.interfaces.presenters.MainStarPresenter
import com.bulingzhuang.gentcomic.interfaces.views.MainStarView
import com.bulingzhuang.gentcomic.utils.database
import com.bulingzhuang.gentcomic.utils.db.ComicStatusRowParser
import com.bulingzhuang.gentcomic.utils.db.DBUtil
import org.jetbrains.anko.db.select

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/4
 * 描    述：
 * ================================================
 */
class MainStarPresenterImpl(private val mView: MainStarView, val context: FragmentActivity) : MainStarPresenter {

    private lateinit var mAdapter: MainStarAdapter

    /**
     * 初始化收藏列表
     */
    override fun initStarAdapter(recyclerView: RecyclerView) {
        mAdapter = MainStarAdapter(context)
        recyclerView.adapter = mAdapter
    }

    /**
     * 刷新收藏列表
     */
    override fun refreshStarList() {
        context.database.use {
            val select = select(DBUtil.TABLE_status)
            val dataList = ArrayList<ComicStatusEntity>(select.parseList(ComicStatusRowParser()))
            val startList = ArrayList<ComicStatusEntity>()
            dataList.forEach {
                if (it.isStar) {
                    startList.add(it)
                }
            }
            mView.hasStarData(startList.isEmpty())
            mAdapter.addAll(startList)
        }
    }
}