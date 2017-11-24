package com.bulingzhuang.gentcomic.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.activities.ComicActivity
import com.bulingzhuang.gentcomic.entity.ComicIndexData
import com.bulingzhuang.gentcomic.utils.showToast

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/16
 * 描    述：漫画列表Adapter
 * ================================================
 */
class ComicIndexAdapter(private val context: Context, private val title: String,private val comicID:String) : RecyclerView.Adapter<ComicIndexAdapter.ComicIndexViewHolder>() {

    private val mDataList = ArrayList<ComicIndexData.ResultBean>()

    fun addAll(collection: Collection<ComicIndexData.ResultBean>) {
        mDataList.addAll(collection)
        notifyItemRangeInserted(0, collection.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ComicIndexViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.adapter_comic_index, parent, false)
        return ComicIndexViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ComicIndexViewHolder?, position: Int) {
        if (holder != null) {
            val item = mDataList[position]
            holder.mTvTitle.text = item.title
            holder.mVRead.visibility = if (item.hasRead) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ComicActivity::class.java)
                intent.putExtra("title", title)
                intent.putExtra("comicID", comicID)
                intent.putExtra("subtitle", item.title)
                intent.putExtra("volsID", item.volsID)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = mDataList.size

    inner class ComicIndexViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val mVRead: View = itemView.findViewById(R.id.v_read)
    }
}