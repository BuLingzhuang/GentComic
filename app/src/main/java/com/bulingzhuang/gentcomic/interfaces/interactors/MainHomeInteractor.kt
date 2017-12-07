package com.bulingzhuang.gentcomic.interfaces.interactors

import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.entity.WeatherData
import com.bulingzhuang.gentcomic.utils.net.ApiCallbackWithPage
import com.bulingzhuang.gentcomic.utils.net.BaseObserver

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/9
 * 描    述：
 * ================================================
 */
interface MainHomeInteractor {

    /**
     * 请求天气数据
     */
    fun requestWeatherData(observer: BaseObserver<WeatherData>, city: String)

    /**
     * 请求主页列表数据
     */
    fun requestMainHomeListData(orderType: String, observer: ApiCallbackWithPage<MainListData>)
}