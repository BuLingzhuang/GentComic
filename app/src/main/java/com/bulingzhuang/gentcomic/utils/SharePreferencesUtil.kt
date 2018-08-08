package com.bulingzhuang.gentcomic.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/13
 * 描    述：
 * ================================================
 */
class SharePreferencesUtil {

    companion object {
        private lateinit var sp: SharedPreferences

        /**
         * 初始化方法
         */
        fun initializeInstance(context: Context) {
            sp = context.getSharedPreferences(Constants.DEADLINE_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        }

        /**
         * 存储数据
         */
        fun setValue(context: Context, key: String, value: Any) {
            val edit = sp.edit()
            when (value) {
                is String -> {
                    edit.putString(key, value).apply()
                }
                is Boolean -> {
                    edit.putBoolean(key, value).apply()
                }
                is Float -> {
                    edit.putFloat(key, value).apply()
                }
                is Int -> {
                    edit.putInt(key, value).apply()
                }
                is Long -> {
                    edit.putLong(key, value).apply()
                }
            }
        }

        /**
         * 获取对应类型的数据
         */
        fun getString(key: String, default: String = ""): String {
            return sp.getString(key, default).let { it } ?: default
        }

        fun getBoolean(key: String, default: Boolean = false): Boolean {
            return sp.getBoolean(key, default)
        }

        fun getFloat(key: String, default: Float = 0f): Float {
            return sp.getFloat(key, default)
        }

        fun getInt(key: String, default: Int = 0): Int {
            return sp.getInt(key, default)
        }

        fun getLong(key: String, default: Long = 0): Long {
            return sp.getLong(key, default)
        }
    }
}