package com.bulingzhuang.gentcomic.entity

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/13
 * 描    述：
 * ================================================
 */
class WeatherData constructor(val results: List<ResultsBean>) {

    data class ResultsBean(val location: LocationBean,
                           val now: NowBean,
                           val last_update: String) {

        data class LocationBean(
                val id: String,
                val name: String,
                val country: String,
                val path: String,
                val timezone: String,
                val timezone_offset: String
        )

        data class NowBean(
                val text: String,
                val code: String,
                val temperature: String,
                var createTime: Long
        )
    }
}