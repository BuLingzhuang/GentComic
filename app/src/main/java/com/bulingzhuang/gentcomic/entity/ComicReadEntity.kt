package com.bulingzhuang.gentcomic.entity

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/24
 * 描    述：
 * ================================================
 */
class ComicReadEntity(val volID: String, val readTimes: Long, val comicID: String, val lastReadDate: Long) {

    override fun toString(): String {
        return "volID = $volID，readTimes = $readTimes，comicID = $comicID，lastReadDate = $lastReadDate"
    }
}