package com.bulingzhuang.gentcomic.impl.interactors

import com.bulingzhuang.gentcomic.entity.MainListData
import com.bulingzhuang.gentcomic.interfaces.interactors.MainInteractor
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
class MainInteractorImpl : BaseInteractorImpl(), MainInteractor {

    /**
     * 请求主页列表数据
     */
    override fun requestMainListData(observer: ApiCallbackWithPage<MainListData>) {
        if (observer.pageNum < 1) {
            addSubscription(ApiClient.retrofit().loadMainList(1), observer)
        } else {
            addSubscription(ApiClient.retrofit().loadMainList(observer.pageNum), observer)
        }
    }
}