package com.bulingzhuang.gentcomic.impl.interactors

import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.entity.WeatherData
import com.bulingzhuang.gentcomic.interfaces.interactors.MainHomeInteractor
import com.bulingzhuang.gentcomic.utils.Constants
import com.bulingzhuang.gentcomic.utils.net.ApiCallbackWithPage
import com.bulingzhuang.gentcomic.utils.net.ApiClient
import com.bulingzhuang.gentcomic.utils.net.BaseObserver

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/9
 * 描    述：
 * ================================================
 */
class MainHomeInteractorImpl : BaseInteractorImpl(), MainHomeInteractor {

    /**
     * 请求天气数据
     */
    override fun requestWeatherData(observer: BaseObserver<WeatherData>, city: String) {
        if (city.isEmpty()) {
            addSubscription(ApiClient.retrofit().loadWeather(), observer)
        } else {
            addSubscription(ApiClient.retrofit().loadWeather(city), observer)
        }
    }

    /**
     * 请求主页列表数据
     */
    override fun requestMainHomeListData(orderType: String, observer: ApiCallbackWithPage<MainListData>) {
        val observable = when (orderType) {
            Constants.ORDER_TYPE_DAILY ->
                if (observer.pageNum < 1) {
                    ApiClient.retrofit().loadMainListDaily(1)
                } else {
                    ApiClient.retrofit().loadMainListDaily(observer.pageNum)
                }
            Constants.ORDER_TYPE_WEEK ->
                if (observer.pageNum < 1) {
                    ApiClient.retrofit().loadMainListWeek(1)
                } else {
                    ApiClient.retrofit().loadMainListWeek(observer.pageNum)
                }
            Constants.ORDER_TYPE_POP ->
                if (observer.pageNum < 1) {
                    ApiClient.retrofit().loadMainListPop(1)
                } else {
                    ApiClient.retrofit().loadMainListPop(observer.pageNum)
                }
            else ->
                if (observer.pageNum < 1) {
                    ApiClient.retrofit().loadMainListDaily(1)
                } else {
                    ApiClient.retrofit().loadMainListDaily(observer.pageNum)
                }
        }
        addSubscription(observable, observer)
    }
}