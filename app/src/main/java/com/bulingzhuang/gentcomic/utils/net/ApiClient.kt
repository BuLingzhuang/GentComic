package com.bulingzhuang.gentcomic.utils.net

import com.bulingzhuang.gentcomic.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/8
 * 描    述：Api客户端
 * ================================================
 */
object ApiClient {
    fun retrofit(): ApiStores {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            //Log信息拦截器
        }
        val okHttpClient = okHttpBuilder.build()
        val retrofitBuilder = Retrofit.Builder()
        val retrofitClient = retrofitBuilder.baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient).build()
        return retrofitClient.create(ApiStores::class.java)
    }
}