package com.bulingzhuang.gentcomic.adapters

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.activities.ComicIndexActivity
import com.bulingzhuang.gentcomic.base.GlideApp
import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.utils.ScrollOffsetTransformer
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import java.util.*
import kotlin.collections.ArrayList

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/10
 * 描    述：主页列表数据Adapter
 * ================================================
 */
class MainListAdapter(private val context: FragmentActivity, private val isFullScreen: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mDataList: MutableList<MainListData.ResultBean> = ArrayList()
    private val random = Random()
    private var mHeaderPagerAdapter: MainListHeaderPagerAdapter? = null
    private var marginPx = 0

    init {
        val scale = context.resources.displayMetrics.density
        marginPx = (scale * 4 + 0.5f).toInt()
    }

    /**
     * 添加全部
     */
    fun addAll(collection: List<MainListData.ResultBean>, isRefresh: Boolean = true) {
        if (collection.isNotEmpty()) {
            if (isRefresh) {
                mDataList.clear()
                //添加第一个作为占位标记，实际展示Header内容
                mDataList.add(collection[0])
                val headerList = ArrayList<View>()

                for (position in collection.indices) {
                    val item = collection[position]
                    if (position > 4) {//列表内容
                        mDataList.add(item)
                    } else {//头部内容
                        //初始化HeaderAdapter
                        val inflate = LayoutInflater.from(context).inflate(R.layout.adapter_main_list_header_item, null) as LinearLayout
                        val ivContent = inflate.findViewById<ImageView>(R.id.iv_content)
                        val build = LazyHeaders.Builder().addHeader("Referer", "http://3gmanhua.com/top/").build()
                        val url = GlideUrl(item.image, build)
                        GlideApp.with(context).load(url).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(ivContent)
                        inflate.findViewById<TextView>(R.id.tv_title).text = item.commicName
                        random(inflate.findViewById(R.id.iv_download),
                                inflate.findViewById(R.id.iv_start),
                                inflate.findViewById(R.id.iv_fire))
                        inflate.findViewById<CardView>(R.id.cv_content).setOnClickListener {
                            val intent = Intent(context, ComicIndexActivity::class.java)
                            intent.putExtra("comic_image", item.image)
                            intent.putExtra("comic_id", item.commicURL)
                            intent.putExtra("comic_title", item.commicName)
                            val options = ActivityOptions.makeSceneTransitionAnimation(context,
                                    Pair(ivContent as View, "Image_Comic_Index_Header")).toBundle()
                            context.startActivity(intent, options)
                        }
                        headerList.add(inflate)
                    }
                }
                mHeaderPagerAdapter = MainListHeaderPagerAdapter(headerList)
                notifyDataSetChanged()
            } else {
                mDataList.addAll(collection)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val item = mDataList[position]
        when (holder?.itemViewType) {
            R.layout.adapter_main_list_header -> {
                val viewHolder = holder as MainListHeaderViewHolder
                viewHolder.mVpHeader.setPageTransformer(true, ScrollOffsetTransformer(isFullScreen))
                viewHolder.mVpHeader.offscreenPageLimit = 2
                viewHolder.mVpHeader.adapter = mHeaderPagerAdapter
                mHeaderPagerAdapter?.count?.div(2)?.let { viewHolder.mVpHeader.currentItem = it }
            }
            R.layout.adapter_main_list -> {
                val viewHolder = holder as MainListContentViewHolder
                viewHolder.mTvTitle.text = item.commicName
                if (position > 0) {
                    when ((position - 1) % 3) {
                        0 -> {//左侧
                            (viewHolder.mCvContent.layoutParams as RelativeLayout.LayoutParams).setMargins(marginPx * 3, marginPx*2, marginPx, marginPx*2)
                        }
                        1 -> {//中间
                            (viewHolder.mCvContent.layoutParams as RelativeLayout.LayoutParams).setMargins(marginPx*2, marginPx*2, marginPx*2, marginPx*2)
                        }
                        2 -> {//右侧
                            (viewHolder.mCvContent.layoutParams as RelativeLayout.LayoutParams).setMargins(marginPx, marginPx*2, marginPx * 3, marginPx*2)
                        }
                    }
                }
                random(viewHolder.mIvDownload, viewHolder.mIvStart, viewHolder.mIvFire)
                val build = LazyHeaders.Builder().addHeader("Referer", "http://3gmanhua.com/top/").build()
                val url = GlideUrl(item.image, build)
                GlideApp.with(context).load(url).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(viewHolder.mIvContent)
                viewHolder.mCvContent.setOnClickListener {
                    val intent = Intent(context, ComicIndexActivity::class.java)
                    intent.putExtra("comic_image", item.image)
                    intent.putExtra("comic_id", item.commicURL)
                    intent.putExtra("comic_title", item.commicName)
                    val options = ActivityOptions.makeSceneTransitionAnimation(context,
                            Pair(viewHolder.mIvContent as View, "Image_Comic_Index_Header")).toBundle()
                    context.startActivity(intent, options)
                }
            }
        }
    }

    /**
     * 随机生成Tint
     */
    private fun random(view0: ImageView, view1: ImageView, view2: ImageView) {
        val randomStr = Integer.toBinaryString(random.nextInt(8))
        when (randomStr.length) {
            1 -> {
                when (randomStr[0]) {
                    '1' -> {
                        view0.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teal600))
                    }
                    '0' -> {
                        view0.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                    }
                }
            }
            2 -> {
                when (randomStr[0]) {
                    '1' -> {
                        view1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.amber600))
                    }
                    '0' -> {
                        view1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                    }
                }
                when (randomStr[1]) {
                    '1' -> {
                        view0.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teal600))
                    }
                    '0' -> {
                        view0.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                    }
                }
            }
            3 -> {
                when (randomStr[0]) {
                    '1' -> {
                        view2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red600))
                    }
                    '0' -> {
                        view2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                    }
                }
                when (randomStr[1]) {
                    '1' -> {
                        view1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.amber600))
                    }
                    '0' -> {
                        view1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                    }
                }
                when (randomStr[2]) {
                    '1' -> {
                        view0.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teal600))
                    }
                    '0' -> {
                        view0.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.adapter_main_list_header -> {
                MainListHeaderViewHolder(inflate)
            }
            R.layout.adapter_main_list -> {
                MainListContentViewHolder(inflate)
            }
            else -> {
                MainListContentViewHolder(inflate)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < 1) {
            R.layout.adapter_main_list_header
        } else {
            R.layout.adapter_main_list
        }
    }

    /**
     * 列表内容
     */
    private inner class MainListContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mCvContent: CardView = itemView.findViewById(R.id.cv_content)
        val mIvContent: ImageView = itemView.findViewById(R.id.iv_content)
        val mTvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val mIvFire: ImageView = itemView.findViewById(R.id.iv_fire)
        val mIvStart: ImageView = itemView.findViewById(R.id.iv_start)
        val mIvDownload: ImageView = itemView.findViewById(R.id.iv_download)
    }

    /**
     * 头部内容
     */
    private inner class MainListHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mVpHeader: ViewPager = itemView.findViewById(R.id.vp_header)
    }
}