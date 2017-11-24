package com.bulingzhuang.gentcomic.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.entity.ComicReadEntity
import com.bulingzhuang.gentcomic.impl.presenters.ComicPresenterImpl
import com.bulingzhuang.gentcomic.interfaces.views.ComicView
import com.bulingzhuang.gentcomic.utils.database
import com.bulingzhuang.gentcomic.utils.db.ComicReadRowParser
import com.bulingzhuang.gentcomic.utils.db.DBUtil
import com.bulingzhuang.gentcomic.utils.showLogE
import com.bulingzhuang.gentcomic.utils.showSnakeBar
import kotlinx.android.synthetic.main.activity_comic.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.replace
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

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
        val comicID = intent.getStringExtra("comicID")

        database.use {
            val select = select(DBUtil.TABLE_read).whereSimple("${DBUtil.READ_volsID} = ?", volsID)
            val dataList = ArrayList<ComicReadEntity>(select.parseList(ComicReadRowParser()))
            when (dataList.size) {
                0 -> {//插入
                    showLogE("插入操作，$comicID")
                    insert(DBUtil.TABLE_read,
                            DBUtil.READ_volsID to volsID,
                            DBUtil.READ_readTimes to 0,
                            DBUtil.READ_comicID to comicID,
                            DBUtil.READ_lastReadDate to System.currentTimeMillis())
                }
                1 -> {//更新
                    showLogE("更新操作，$comicID")
                    val item = dataList[0]
                    update(DBUtil.TABLE_read, DBUtil.READ_readTimes to item.readTimes + 1, DBUtil.READ_lastReadDate to System.currentTimeMillis())
                            .whereArgs("${DBUtil.READ_volsID} = {${DBUtil.READ_volsID}}", DBUtil.READ_volsID to volsID).exec()
                }
                else -> {
                }
            }
        }

        mPresenter.init(this, tv_battery, tv_time, tv_net, rv_content)
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
