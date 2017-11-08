package com.bulingzhuang.gentcomic.utils.net

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/8
 * 描    述：基础Observer
 * ================================================
 */
abstract class BaseObserver<BLZ> : Observer<BLZ> {
    var disposable: Disposable? = null

    override fun onSubscribe(d: Disposable?) {
        disposable = d
    }
}