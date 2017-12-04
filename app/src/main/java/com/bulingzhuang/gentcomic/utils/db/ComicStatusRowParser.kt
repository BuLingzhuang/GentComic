package com.bulingzhuang.gentcomic.utils.db

import com.bulingzhuang.gentcomic.entity.ComicReadEntity
import com.bulingzhuang.gentcomic.entity.ComicStatusEntity
import com.bulingzhuang.gentcomic.utils.showLogE
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
        val isStar = when (columns[1] as String) {
            "1" -> true
            else -> false
        }
        val isDownload = when (columns[2] as String) {
            "1" -> true
            else -> false
        }
        val comicStatusEntity = ComicStatusEntity(columns[0] as String, isStar, isDownload)
        showLogE("获取的数据：$comicStatusEntity")
        return comicStatusEntity
    }
}