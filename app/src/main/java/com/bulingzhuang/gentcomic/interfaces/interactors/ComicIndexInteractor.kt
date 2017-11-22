package com.bulingzhuang.gentcomic.interfaces.interactors

import com.bulingzhuang.gentcomic.entity.ComicIndexData
import com.bulingzhuang.gentcomic.utils.net.ApiCallback

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/14
 * 描    述：
 * ================================================
 */
interface ComicIndexInteractor {

    /**
     * 请求漫画目录数据
     */
    fun requestComicIndexData(comicID: String, observer: ApiCallback<ComicIndexData>)
}