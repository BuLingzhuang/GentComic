package com.bulingzhuang.gentcomic

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                tv_title.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_star -> {
                tv_title.setText(R.string.title_star)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_download -> {
                tv_title.setText(R.string.title_download)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mine -> {
                tv_title.setText(R.string.title_mine)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        val map = HashMap<String,String>()
//        map.put("Referer", "http://3gmanhua.com")
        val property = System.getProperty("http.agent")
        Log.e("blz","property=$property")
//        map.put("Referer", property)
        //.addHeader("Referer", "http://3gmanhua.com")
        val build = LazyHeaders.Builder().addHeader("Referer", "http://3gmanhua.com/top/").build()
        val url = GlideUrl("http://imgs.hhxiee.net/comicui/33117.jpg",build)
        Glide.with(this).load(url).into(iv)
    }
}
