package com.bulingzhuang.gentcomic.utils.net

import retrofit2.HttpException

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/8
 * 描    述：Api回调
 * ================================================
 */
abstract class ApiCallback<BLZ> : BaseObserver<BLZ>() {

    abstract fun onSuccess(module: BLZ)
    abstract fun onFailure(msg: String?)
    abstract fun onFinish()

    override fun onNext(t: BLZ) {
        onSuccess(t)
    }

    override fun onError(e: Throwable?) {
        if (e is HttpException) {
            val code = e.code()
            var msg = e.message()
            when (code) {
                504 -> msg = "网络炸了"
                502, 404 -> msg = "服务器炸了"
            }
            onFailure(msg)
        } else {
            onFailure(e.toString())
        }
        onFinish()
    }

    override fun onComplete() {
        onFinish()
    }
}