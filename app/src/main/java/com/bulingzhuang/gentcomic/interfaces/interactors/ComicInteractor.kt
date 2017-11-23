package com.bulingzhuang.gentcomic.interfaces.interactors

import com.bulingzhuang.gentcomic.entity.ComicData
import com.bulingzhuang.gentcomic.entity.ImageData
import com.bulingzhuang.gentcomic.utils.net.ApiCallback

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/22
 * 描    述：
 * ================================================
 */
interface ComicInteractor {
    /**
     * 请求漫画数据
     */
    fun requestComicData(volsID: String, observer: ApiCallback<ComicData>)

    /**
     * 处理漫画宽度
     */
    fun handleImageList(imageList: ArrayList<String>, observer: ApiCallback<ImageData>)
}