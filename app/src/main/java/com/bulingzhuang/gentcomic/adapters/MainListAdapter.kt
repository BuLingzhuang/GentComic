package com.bulingzhuang.gentcomic.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.utils.showLogE
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import java.util.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/10
 * 描    述：主页列表数据Adapter
 * ================================================
 */
class MainListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mDataList: MutableList<MainListData.ResultBean> = ArrayList()
    private val random = Random()

    /**
     * 添加全部
     */
    fun addAll(collection:Collection<MainListData.ResultBean>,isRefresh:Boolean = true) {
        if (isRefresh) {
            mDataList.clear()
        }
        mDataList.addAll(collection)
        notifyDataSetChanged()
    }

    /**
     * 添加单条
     */
    fun add(item:MainListData.ResultBean){
        mDataList.add(item)
        notifyItemInserted(0)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val item = mDataList[position]
        when (holder?.itemViewType) {
            R.layout.adapter_main_list_header -> {
            }
            R.layout.adapter_main_list -> {
                val viewHolder = holder as MainListContentViewHolder
                val randomStr = Integer.toBinaryString(random.nextInt(8))
                when (randomStr.length) {
                    1 -> {
                        when (randomStr[0]) {
                            '1' -> {
                                viewHolder.mIvDownload.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teal600))
                            }
                            '0' -> {
                                viewHolder.mIvDownload.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                            }
                        }
                    }
                    2 -> {
                        when (randomStr[0]) {
                            '1' -> {
                                viewHolder.mIvStart.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.amber600))
                            }
                            '0' -> {
                                viewHolder.mIvStart.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                            }
                        }
                        when (randomStr[1]) {
                            '1' -> {
                                viewHolder.mIvDownload.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teal600))
                            }
                            '0' -> {
                                viewHolder.mIvDownload.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                            }
                        }
                    }
                    3 -> {
                        when (randomStr[0]) {
                            '1' -> {
                                viewHolder.mIvFire.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red600))
                            }
                            '0' -> {
                                viewHolder.mIvFire.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                            }
                        }
                        when (randomStr[1]) {
                            '1' -> {
                                viewHolder.mIvStart.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.amber600))
                            }
                            '0' -> {
                                viewHolder.mIvStart.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                            }
                        }
                        when (randomStr[2]) {
                            '1' -> {
                                viewHolder.mIvDownload.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teal600))
                            }
                            '0' -> {
                                viewHolder.mIvDownload.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.disable_gray))
                            }
                        }
                    }
                }

                val build = LazyHeaders.Builder().addHeader("Referer", "http://3gmanhua.com/top/").build()
                val url = GlideUrl(item.image, build)
                Glide.with(context).load(url).into(viewHolder.mIvContent)
                viewHolder.mTvTitle.text = item.commicName
                viewHolder.itemView.setOnClickListener {
                    //TODO 跳转
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.adapter_main_list_header -> {
                MainListContentViewHolder(inflate)
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
//        return if (position < 5) {
//            R.layout.adapter_main_list_header
//        } else {
//            R.layout.adapter_main_list
//        }
        //TODO 暂时返回一种类型
        return R.layout.adapter_main_list
    }

    /**
     * 列表内容
     */
    private inner class MainListContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mIvContent: ImageView = itemView.findViewById(R.id.iv_content)
        val mTvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val mIvFire: ImageView = itemView.findViewById(R.id.iv_fire)
        val mIvStart: ImageView = itemView.findViewById(R.id.iv_start)
        val mIvDownload: ImageView = itemView.findViewById(R.id.iv_download)
    }
}