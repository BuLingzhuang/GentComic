package com.bulingzhuang.gentcomic.utils.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/24
 * 描    述：
 * ================================================
 */
class DBUtil(context: Context) : ManagedSQLiteOpenHelper(context, DB_NAME) {

    companion object {
        val DB_NAME = "gent_comic_db"

        val TABLE_read = "gent_t_read"
        val READ_volsID = "vols_id"
        val READ_readTimes = "read_times"
        val READ_comicID = "comic_id"
        val READ_lastReadDate = "last_read_date"

        val TABLE_status = "gent_t_status"
        val STATUS_comicID = "status_comic_id"
        val STATUS_title = "status_title"
        val STATUS_imageUrl = "status_image_url"
        val STATUS_isStar = "status_is_star"
        val STATUS_isDownload = "status_is_download"
        val STATUS_createTime = "status_create_time"

        private var instance: DBUtil? = null

        fun getInstance(context: Context): DBUtil {
            if (instance == null) {
                instance = DBUtil(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(TABLE_read, false,
                READ_volsID to TEXT + PRIMARY_KEY,
                READ_readTimes to INTEGER + NOT_NULL,
                READ_comicID to TEXT,
                READ_lastReadDate to INTEGER)
        db?.createTable(TABLE_status, false,
                STATUS_comicID to TEXT + PRIMARY_KEY,
                STATUS_title to TEXT,
                STATUS_imageUrl to TEXT,
                STATUS_isStar to TEXT,
                STATUS_isDownload to TEXT,
                STATUS_createTime to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}