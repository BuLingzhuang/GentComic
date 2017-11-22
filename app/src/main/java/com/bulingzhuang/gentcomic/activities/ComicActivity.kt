package com.bulingzhuang.gentcomic.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.impl.presenters.ComicPresenterImpl
import kotlinx.android.synthetic.main.activity_comic.*

class ComicActivity : AppCompatActivity() {

    private val mPresenter = ComicPresenterImpl()

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

        mPresenter.initStatusReceiver(this,tv_battery, tv_time,tv_net)

        tv_title.text = title
        tv_subtitle.text = subtitle
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy(this)
    }
}
