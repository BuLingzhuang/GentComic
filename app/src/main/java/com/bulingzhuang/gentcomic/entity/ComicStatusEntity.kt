package com.bulingzhuang.gentcomic.entity

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/1
 * 描    述：
 * ================================================
 */
class ComicStatusEntity(val comicID: String, var isStar: Boolean, var isDownload: Boolean) {

    override fun toString(): String {
        return "comicID = $comicID，isStar = $isStar，isDownload = $isDownload"
    }
}