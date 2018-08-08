package com.bulingzhuang.gentcomic.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.base.GlideApp
import com.bulingzhuang.gentcomic.entity.ImageData
import com.bulingzhuang.gentcomic.utils.showLogE
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/22
 * 描    述：
 * ================================================
 */
class ComicAdapter(private val context: Context) : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    private val mDataList = ArrayList<String>()

    fun add(item: String) {
        mDataList.add(item)
        notifyDataSetChanged()
    }

    fun addAll(collection: Collection<String>){
        mDataList.clear()
        mDataList.addAll(collection)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.adapter_comic, parent, false)
        return ComicViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val item = mDataList[position]
//            val layoutParams = holder.itemView.layoutParams
//            showLogE("计算前宽 = ${layoutParams.width}，计算前高 = ${layoutParams.height}")
//            layoutParams.width = (layoutParams.height * item.aspectRatio).toInt()
        GlideApp.with(context).load(item).placeholder(R.mipmap.loading).error(R.mipmap.loading).transition(DrawableTransitionOptions.withCrossFade()).into(holder.itemView as ImageView)
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}