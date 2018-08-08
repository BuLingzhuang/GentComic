package com.bulingzhuang.gentcomic.activities

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.base.GlideApp
import com.bulingzhuang.gentcomic.impl.presenters.ComicIndexPresenterImpl
import com.bulingzhuang.gentcomic.interfaces.views.ComicIndexView
import com.bulingzhuang.gentcomic.utils.SystemStatusManager
import com.bulingzhuang.gentcomic.utils.showSnakeBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_comic_index.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 版    本：1.0
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/17
 * 描    述：漫画列表页面
 * ================================================
 */
class ComicIndexActivity : AppCompatActivity(), View.OnClickListener, ComicIndexView {

    private val mPresenter = ComicIndexPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_index)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val systemStatusManager = SystemStatusManager(this)
        systemStatusManager.isStatusBarTintEnabled = true
        systemStatusManager.setStatusBarAlpha(0f)
        init()
    }

    private fun init() {
        val comicID = intent.getStringExtra("comic_id")
        val imageUrl = intent.getStringExtra("comic_image")
        val title = intent.getStringExtra("comic_title")
        mPresenter.checkStatus(this, title, imageUrl, comicID)
        mPresenter.initAdapter(this, rv_content, title, comicID)
        setViewsOnClickListener(rl_back, rl_share, ll_star, ll_download)
        tv_title.text = title
        GlideApp.with(this).asDrawable().load(imageUrl).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                if (resources != null) {
                    iv_header.setImageDrawable(resource)
                }
                Glide.with(this@ComicIndexActivity)
                        .load(resource)
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(3)))
                        .into(iv_headerBg)
            }
        })
        mPresenter.getComicIndexData(this, comicID)
    }

    private fun setViewsOnClickListener(vararg views: View) {
        views.forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> {
                finish()
            }
            R.id.rl_share -> {
            }
            R.id.ll_star -> mPresenter.clickStar(this)
            R.id.ll_download -> mPresenter.clickDownload(this)
        }
    }

    /**
     * 刷新star、download状态
     */
    override fun refreshStatus(isStar: Boolean, isDownload: Boolean) {
        if (isStar) {
            iv_star.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.amber600))
            tv_star.text = "取消收藏"
            tv_star.setTextColor(ContextCompat.getColor(this, R.color.amber600))
        } else {
            iv_star.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.white))
            tv_star.text = "收藏"
            tv_star.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }
        if (isDownload) {
            iv_download.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.teal600))
            tv_download.text = "已下载"
            tv_download.setTextColor(ContextCompat.getColor(this, R.color.teal600))
        } else {
            iv_download.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.white))
            tv_download.text = "下载"
            tv_download.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }
    }

    override fun showSnakeBar(msg: String) {
        showSnakeBar(msg, cl_gen)
    }

    override fun showRefreshing(show: Boolean) {
        if (show) {
            pb.visibility = View.VISIBLE
        } else {
            pb.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
