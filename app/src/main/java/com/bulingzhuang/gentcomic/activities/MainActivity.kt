package com.bulingzhuang.gentcomic.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.adapters.CommonPagerAdapter
import com.bulingzhuang.gentcomic.base.BaseFragment
import com.bulingzhuang.gentcomic.fragments.MainHomeFragment
import com.bulingzhuang.gentcomic.fragments.MainStarFragment
import com.bulingzhuang.gentcomic.fragments.TestFragment
import com.bulingzhuang.gentcomic.utils.Constants
import com.bulingzhuang.gentcomic.utils.SharePreferencesUtil
import com.bulingzhuang.gentcomic.utils.setViewsOnClickListener
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
class MainActivity : AppCompatActivity(), View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private val mFragmentList = ArrayList<BaseFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private lateinit var mOrderMenu: PopupMenu

    private fun init() {
        mOrderMenu = PopupMenu(this, rl_order)
        mOrderMenu.setOnMenuItemClickListener(this)
        mOrderMenu.inflate(R.menu.order)

        checkOrderType()
        setViewsOnClickListener(this, iv_search, rl_order)

        mFragmentList.add(MainHomeFragment.newInstance(resources.getString(R.string.title_home)))
        mFragmentList.add(MainStarFragment.newInstance(resources.getString(R.string.title_star)))
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
                    rl_order.scaleX = offset
                    rl_order.scaleY = offset
                    rl_order.alpha = offset
                } else {
                    iv_search.scaleX = 0f
                    iv_search.scaleY = 0f
                    iv_search.alpha = 0f
                    rl_order.scaleX = 0f
                    rl_order.scaleY = 0f
                    rl_order.alpha = 0f
                }
            }

            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }
        })

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

    /**
     * 检查列表类型
     */
    private fun checkOrderType() {
        val orderType = SharePreferencesUtil.getString(Constants.SP_ORDER_TYPE, Constants.ORDER_TYPE_DAILY)
        when (orderType) {
            Constants.ORDER_TYPE_DAILY -> {
                iv_order_letter.setImageResource(R.drawable.ic_letter_d)
            }
            Constants.ORDER_TYPE_WEEK -> {
                iv_order_letter.setImageResource(R.drawable.ic_letter_w)
            }
            Constants.ORDER_TYPE_POP -> {
                iv_order_letter.setImageResource(R.drawable.ic_letter_p)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_search -> {
                //TODO 搜索
            }
            R.id.rl_order -> {
                mOrderMenu.show()
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.order_daily -> {
                SharePreferencesUtil.setValue(this, Constants.SP_ORDER_TYPE, Constants.ORDER_TYPE_DAILY)
                iv_order_letter.setImageResource(R.drawable.ic_letter_d)
                //TODO 刷新HOME页面
                true
            }
            R.id.order_week -> {
                SharePreferencesUtil.setValue(this, Constants.SP_ORDER_TYPE, Constants.ORDER_TYPE_WEEK)
                iv_order_letter.setImageResource(R.drawable.ic_letter_w)
                true
            }
            R.id.order_pop -> {
                SharePreferencesUtil.setValue(this, Constants.SP_ORDER_TYPE, Constants.ORDER_TYPE_POP)
                iv_order_letter.setImageResource(R.drawable.ic_letter_p)
                true
            }
            else -> false
        }
    }
}
