package com.bulingzhuang.gentcomic.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.adapters.CommonPagerAdapter
import com.bulingzhuang.gentcomic.base.BaseFragment
import com.bulingzhuang.gentcomic.fragments.HomeFragment
import com.bulingzhuang.gentcomic.fragments.TestFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 版    本：1.0
 * 创建日期：2017/11/8
 * 描    述：首页
 * 修订历史：
 * ================================================
 */
class MainActivity : AppCompatActivity() {

    private val mFragmentList = ArrayList<BaseFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        mFragmentList.add(HomeFragment.newInstance(resources.getString(R.string.title_home)))
        mFragmentList.add(TestFragment.newInstance(resources.getString(R.string.title_star)))
        mFragmentList.add(TestFragment.newInstance(resources.getString(R.string.title_download)))
        mFragmentList.add(TestFragment.newInstance(resources.getString(R.string.title_mine)))
        vp_content.adapter = CommonPagerAdapter(supportFragmentManager, mFragmentList)
        vp_content.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 0) {
                    val offset = 1 - positionOffset
                    iv_search.scaleX = offset
                    iv_search.scaleY = offset
                    iv_search.alpha = offset
                }
            }

            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }
        })
        iv_search.setOnClickListener {
            //TODO 搜索页面
        }
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    vp_content.setCurrentItem(0, true)
                    tv_title.setText(R.string.title_home)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_star -> {
                    vp_content.setCurrentItem(1, true)
                    tv_title.setText(R.string.title_star)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_download -> {
                    vp_content.setCurrentItem(2, true)
                    tv_title.setText(R.string.title_download)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_mine -> {
                    vp_content.setCurrentItem(3, true)
                    tv_title.setText(R.string.title_mine)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
}
