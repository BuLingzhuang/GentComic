package com.bulingzhuang.gentcomic

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bulingzhuang.gentcomic.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test)
//        mBinding.title.text = "我是标题"
//        mBinding.apply {
//            title.text = "我是标题"
//            author.text = "作者"
//            content.text = "正文内容正文内容"
//            date.text = "2017-11-08"
//        }
//        val item = Item("标题", "卜令壮", "今天也好好写代码了", "2017-11-08")
        val item = Item("日记", "卜令壮", "今天也好好写代码了", "2017-11-08")
        mBinding.item = item
        mBinding.executePendingBindings()

        //模拟修改了数据，要刷新列表
        Handler().postDelayed({ item.author = "鲁迅" }, 3000)
    }


}
