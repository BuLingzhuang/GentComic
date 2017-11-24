package com.bulingzhuang.gentcomic.utils.db

import com.bulingzhuang.gentcomic.entity.ComicReadEntity
import com.bulingzhuang.gentcomic.utils.showLogE
import org.jetbrains.anko.db.RowParser

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/24
 * 描    述：
 * ================================================
 */
class ComicReadRowParser : RowParser<ComicReadEntity> {
    override fun parseRow(columns: Array<Any?>): ComicReadEntity {
        val comicReadEntity = ComicReadEntity(columns[0] as String, columns[1] as Long, columns[2] as String, columns[3] as Long)
        showLogE("获取的数据：$comicReadEntity")
        return comicReadEntity
    }
}