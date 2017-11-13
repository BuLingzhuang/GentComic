package com.bulingzhuang.gentcomic.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.impl.presenters.MainPresenterImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.MainPresenter
import com.bulingzhuang.gentcomic.interfaces.views.MainView
import com.bulingzhuang.gentcomic.utils.showSnakeBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
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
class MainActivity : AppCompatActivity(), MainView {

    private lateinit var mPresenter:MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        mPresenter.getMainListData(this)
    }

    private fun init() {
        mPresenter = MainPresenterImpl(this)
        mPresenter.initAdapter(this,rv_content)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    tv_title.setText(R.string.title_home)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_star -> {
                    tv_title.setText(R.string.title_star)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_download -> {
                    tv_title.setText(R.string.title_download)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_mine -> {
                    tv_title.setText(R.string.title_mine)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        srl_content.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        srl_content.setOnRefreshListener {
            mPresenter.getMainListData(this)
        }
    }

    override fun updateError(eText: String?) {
        showSnakeBar(eText?.let { it } ?: "请求失败", cl_gen)
    }

    override fun setRefreshing(refreshing: Boolean) {
        srl_content.isRefreshing = refreshing
    }
}
