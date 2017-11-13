package com.bulingzhuang.gentcomic.impl.interactors

import com.bulingzhuang.gentcomic.utils.net.BaseObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/9
 * 描    述：
 * ================================================
 */
open class BaseInteractorImpl {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun doBeforeDestroy() {
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    fun <BLZ> addSubscription(observable: Observable<BLZ>, observer: BaseObserver<BLZ>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        mCompositeDisposable.add(observer.disposable)
    }
}