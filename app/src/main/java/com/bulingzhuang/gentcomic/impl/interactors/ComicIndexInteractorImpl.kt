package com.bulingzhuang.gentcomic.impl.interactors

import com.bulingzhuang.gentcomic.entity.ComicIndexData
import com.bulingzhuang.gentcomic.interfaces.interactors.ComicIndexInteractor
import com.bulingzhuang.gentcomic.utils.net.ApiCallback
import com.bulingzhuang.gentcomic.utils.net.ApiClient

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/14
 * 描    述：
 * ================================================
 */
class ComicIndexInteractorImpl : BaseInteractorImpl(), ComicIndexInteractor {

    /**
     * 请求漫画目录数据
     */
    override fun requestComicIndexData(comicID: String, observer: ApiCallback<ComicIndexData>) {
        addSubscription(ApiClient.retrofit().loadComicIndex(comicID), observer)
    }
}