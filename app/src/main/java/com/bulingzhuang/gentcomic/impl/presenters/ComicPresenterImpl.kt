package com.bulingzhuang.gentcomic.impl.presenters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.bulingzhuang.gentcomic.adapters.ComicAdapter
import com.bulingzhuang.gentcomic.entity.ComicData
import com.bulingzhuang.gentcomic.entity.ImageData
import com.bulingzhuang.gentcomic.impl.interactors.ComicInteractorImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.ComicPresenter
import com.bulingzhuang.gentcomic.interfaces.views.ComicView
import com.bulingzhuang.gentcomic.receiver.ComicReceiver
import com.bulingzhuang.gentcomic.utils.net.ApiCallback
import java.util.*
import kotlin.collections.ArrayList

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/22
 * 描    述：
 * ================================================
 */
class ComicPresenterImpl(private val mView: ComicView) : ComicPresenter {

    private lateinit var mReceiver: ComicReceiver
    private val mInteractor = ComicInteractorImpl()
    private lateinit var mAdapter: ComicAdapter

    /**
     * 初始化各类状态监听
     */
    @SuppressLint("SetTextI18n")
    override fun init(context: Context, tvBattery: TextView, ivCharging: ImageView, tvTime: TextView, tvNet: TextView, rvContent: RecyclerView) {
        val calendar = Calendar.getInstance()
        tvTime.text = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

        mReceiver = ComicReceiver(tvBattery, ivCharging, tvTime, tvNet)
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_CHANGED)
        filter.addAction(Intent.ACTION_TIME_TICK)
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(mReceiver, filter)
        mAdapter = ComicAdapter(context)
        rvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvContent.adapter = mAdapter
    }

    /**
     * 获取漫画数据
     */
    override fun getComicData(context: Context, volsID: String) {
        mView.showRefreshing()
        mInteractor.requestComicData(volsID, object : ApiCallback<ComicData>() {
            override fun onSuccess(module: ComicData) {
//                handleImageList(module.result)
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
     * 处理漫画宽度(垃圾方法，完全多此一举，但写的很多，不删了)
     */
    private fun handleImageList(imageList: ArrayList<String>) {
        mInteractor.handleImageList(imageList, object : ApiCallback<ImageData>() {
            override fun onSuccess(module: ImageData) {
                if (module.aspectRatio > 0f) {
//                    mAdapter.add(module)
                }
            }

            override fun onFailure(msg: String?) {}

            override fun onFinish() {}
        })
    }

    /**
     * 销毁操作
     */
    override fun onDestroy(context: Context) {
        context.unregisterReceiver(mReceiver)
        mInteractor.doBeforeDestroy()
    }
}