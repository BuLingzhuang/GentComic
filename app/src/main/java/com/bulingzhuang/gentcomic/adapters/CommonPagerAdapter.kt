package com.bulingzhuang.gentcomic.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bulingzhuang.gentcomic.base.BaseFragment

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/1
 * 描    述：
 * ================================================
 */
class CommonPagerAdapter(fm: FragmentManager, private val mFragmentList: MutableList<BaseFragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentList[position].title
    }
}