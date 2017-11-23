package com.bulingzhuang.gentcomic.impl.interactors

import android.graphics.BitmapFactory
import com.bulingzhuang.gentcomic.entity.ComicData
import com.bulingzhuang.gentcomic.entity.ImageData
import com.bulingzhuang.gentcomic.interfaces.interactors.ComicInteractor
import com.bulingzhuang.gentcomic.utils.net.ApiCallback
import com.bulingzhuang.gentcomic.utils.net.ApiClient
import com.bulingzhuang.gentcomic.utils.net.BaseObserver
import com.bulingzhuang.gentcomic.utils.showLogE
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/22
 * 描    述：
 * ================================================
 */
class ComicInteractorImpl : BaseInteractorImpl(), ComicInteractor {

    /**
     * 请求漫画数据
     */
    override fun requestComicData(volsID: String, observer: ApiCallback<ComicData>) {
        addSubscription(ApiClient.retrofit().loadComic(volsID), observer)
    }

    /**
     * 处理漫画宽度
     */
    override fun handleImageList(imageList: ArrayList<String>, observer: ApiCallback<ImageData>) {
        Observable.create<String> {
            for (imageStr in imageList) {
                it.onNext(imageStr)
            }
        }.map {
            try {
                val url = URL(it)
                val con = url.openConnection() as HttpURLConnection
                val inStream = con.inputStream
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeStream(inStream, null, options)
                val aspectRatio = options.outWidth.toFloat() / options.outHeight
                inStream.close()
                return@map ImageData(it, aspectRatio)
            } catch (e: Exception) {
                showLogE("请求图片宽度报错了")
                return@map ImageData(it, 0f)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer)
    }
}