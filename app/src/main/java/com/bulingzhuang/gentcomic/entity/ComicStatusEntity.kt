package com.bulingzhuang.gentcomic.entity

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/1
 * 描    述：
 * ================================================
 */
class ComicStatusEntity(val comicID: String, val title: String, val imageUrl: String, var isStar: Boolean, var isDownload: Boolean, val createTime: Long) {

    override fun toString(): String {
        return "comicID = $comicID，title = $title，imageUrl = $imageUrl，isStar = $isStar，isDownload = $isDownload，createTime = $createTime"
    }
}