package com.bulingzhuang.gentcomic.utils.net

import com.bulingzhuang.gentcomic.entity.ComicData
import com.bulingzhuang.gentcomic.entity.ComicIndexData
import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.entity.WeatherData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * ================================================
 * 作    者：bulingzhuang
 * 版    本：1.0
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/8
 * 描    述：Api合集
 * ================================================
 */
interface ApiStores {
    companion object {
        val API_SERVER_URL = "http://39.106.7.250:8080/"
        val API_NEW_WEATHER_SERVER_URL = "https://api.seniverse.com/"
    }
//    http://39.106.7.250:8080/comic/vols/3234_28320
//    /comic/top/daily/{pageNum}
//    漫画
//    /comic/{comicID}
//    漫画下具体哪一集 返回图片列表直接
//    /comic/vols/{volsID}

    @GET("v3/weather/now.json")
    fun loadWeather(@Query("location") location: String = "上海", @Query("key") key: String = "0dgqeyrbpaxdhejn",
                    @Query("language") language: String = "zh-Hans", @Query("unit") unit: String = "c"): Observable<WeatherData>

    /**
     * 获取主页列表协议
     */
    @GET("comic/top/daily/{pageNum}")
    fun loadMainList(@Path("pageNum") pageNum: Int): Observable<MainListData>

    /**
     * 获取漫画目录协议
     */
    @GET("/comic/{comicID}")
    fun loadComicIndex(@Path("comicID") comicID: String): Observable<ComicIndexData>

    /**
     * 获取漫画协议
     */
    @GET("/comic/vols/{volsID}")
    fun loadComic(@Path("volsID") volsID: String): Observable<ComicData>
}