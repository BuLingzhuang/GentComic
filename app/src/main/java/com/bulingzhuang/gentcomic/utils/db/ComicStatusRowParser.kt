package com.bulingzhuang.gentcomic.utils.db

import com.bulingzhuang.gentcomic.entity.ComicStatusEntity
import org.jetbrains.anko.db.RowParser

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/1
 * 描    述：
 * ================================================
 */
class ComicStatusRowParser : RowParser<ComicStatusEntity> {
    override fun parseRow(columns: Array<Any?>): ComicStatusEntity {
        val isStar = when (columns[3] as String) {
            "1" -> true
            else -> false
        }
        val isDownload = when (columns[4] as String) {
            "1" -> true
            else -> false
        }
        //        showLogE("获取的数据：$comicStatusEntity")
        return ComicStatusEntity(columns[0] as String, columns[1] as String, columns[2] as String, isStar, isDownload, columns[5] as Long)
    }
}