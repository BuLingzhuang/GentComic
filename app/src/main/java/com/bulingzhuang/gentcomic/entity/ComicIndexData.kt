package com.bulingzhuang.gentcomic.entity

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/14
 * 描    述：漫画目录实体类
 * ================================================
 */

class ComicIndexData(val result: List<ResultBean>, code: Int, message: String) : BaseData(code, message) {
    class ResultBean(val volsID: String, val title: String)
}
