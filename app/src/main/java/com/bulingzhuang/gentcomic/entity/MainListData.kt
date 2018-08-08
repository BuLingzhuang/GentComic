package com.bulingzhuang.gentcomic.entity

/**
 * ================================================
 * 作    者：bulingzhuang
 * 版    本：1.0
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/8
 * 描    述：主页列表类
 * ================================================
 */
class MainListData(val data: List<ResultBean>, code: Int, message: String) : BaseData(code, message) {

    class ResultBean(val cover: String, val comicName: String, val comicID: String)
}
