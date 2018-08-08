package com.bulingzhuang.gentcomic.adapters

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.activities.ComicIndexActivity
import com.bulingzhuang.gentcomic.base.GlideApp
import com.bulingzhuang.gentcomic.entity.ComicStatusEntity
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.util.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/4
 * 描    述：
 * ================================================
 */
class MainStarAdapter(private val context: FragmentActivity) : RecyclerView.Adapter<MainStarAdapter.MainStarContentViewHolder>() {

    private val mDataList = ArrayList<ComicStatusEntity>()
    private val random = Random()
    private val marginPx = (context.resources.displayMetrics.density * 5 + 0.5f).toInt()

    fun addAll(collection: Collection<ComicStatusEntity>) {
        mDataList.clear()
        mDataList.addAll(collection)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainStarContentViewHolder, position: Int) {
        val item = mDataList[position]
        when (position % 3) {
            0 -> {//左侧
                (holder.mLlContent.layoutParams as android.support.v7.widget.GridLayoutManager.LayoutParams).setMargins(marginPx * 3, marginPx * 2, marginPx, marginPx * 2)
            }
            1 -> {//中间
                (holder.mLlContent.layoutParams as android.support.v7.widget.GridLayoutManager.LayoutParams).setMargins(marginPx * 2, marginPx * 2, marginPx * 2, marginPx * 2)
            }
            2 -> {//右侧
                (holder.mLlContent.layoutParams as android.support.v7.widget.GridLayoutManager.LayoutParams).setMargins(marginPx, marginPx * 2, marginPx * 3, marginPx * 2)
            }
        }
        val build = LazyHeaders.Builder().addHeader("Referer", "http://3gmanhua.com/top/").build()
        val url = GlideUrl(item.imageUrl, build)
        GlideApp.with(context).load(url).placeholder(R.mipmap.loading).transition(DrawableTransitionOptions.withCrossFade()).error(R.mipmap.loading).into(holder.mIvContent)
        holder.mTvTitle.text = item.title
        holder.mIvFire.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red600))
        if (item.isStar) {
            holder.mIvStar.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.amber600))
        } else {
            holder.mIvStar.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
        }
        if (item.isDownload) {
            holder.mIvDownload.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teal600))
        } else {
            holder.mIvDownload.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
        }
        holder.mLlContent.setOnClickListener {
            val intent = Intent(context, ComicIndexActivity::class.java)
            intent.putExtra("comic_image", item.imageUrl)
            intent.putExtra("comic_id", item.comicID)
            intent.putExtra("comic_title", item.title)
            val options = ActivityOptions.makeSceneTransitionAnimation(context,
                    Pair(holder.mIvContent as View, "Image_Comic_Index_Header")).toBundle()
            context.startActivity(intent, options)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainStarContentViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.adapter_main_home, parent, false)
        return MainStarContentViewHolder(inflate)
    }

    override fun getItemCount(): Int = mDataList.size

    /**
     * 列表内容
     */
    inner class MainStarContentViewHolder(itemView: View) : MainHomeAdapter.MainHomeContentViewHolder(itemView)
}