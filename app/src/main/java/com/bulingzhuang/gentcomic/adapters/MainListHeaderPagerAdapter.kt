package com.bulingzhuang.gentcomic.adapters

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/13
 * 描    述：
 * ================================================
 */
open class MainListHeaderPagerAdapter(private var views: List<View>) : PagerAdapter() {
    override fun getCount(): Int {
        return views.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views[position]
    }
}