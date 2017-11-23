package com.bulingzhuang.gentcomic.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.impl.presenters.ComicPresenterImpl
import com.bulingzhuang.gentcomic.interfaces.views.ComicView
import com.bulingzhuang.gentcomic.utils.showLogE
import com.bulingzhuang.gentcomic.utils.showSnakeBar
import kotlinx.android.synthetic.main.activity_comic.*

class ComicActivity : AppCompatActivity(), ComicView {

    private val mPresenter = ComicPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_comic)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        val title = intent.getStringExtra("title")
        val subtitle = intent.getStringExtra("subtitle")
        val volsID = intent.getStringExtra("volsID")

        showLogE("宽度 = ${rl_gen.layoutParams.width}，高度 = ${rl_gen.layoutParams.height}")
        mPresenter.init(this, tv_battery, tv_time, tv_net,rv_content)
        mPresenter.getComicData(this, volsID)

        tv_title.text = title
        tv_subtitle.text = subtitle
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy(this)
    }

    override fun showSnakeBar(msg: String) {
        showSnakeBar(msg, rl_gen)
    }

    override fun showRefreshing(show: Boolean) {
        if (show) {
            pb.visibility = View.VISIBLE
        } else {
            pb.visibility = View.GONE
        }
    }
}
