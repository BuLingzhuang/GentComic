package com.bulingzhuang.gentcomic

import android.databinding.BaseObservable
import android.databinding.Bindable

/**
 * Created by bulingzhuang
 * on 2017/11/8
 * E-mail:bulingzhuang@foxmail.com
 */
class Item(title: String, author: String, content: String, date: String) : BaseObservable() {

    @get:Bindable
    var title: String = title
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
    @get:Bindable
    var author: String = author
        set(value) {
            field = value
            notifyPropertyChanged(BR.author)
        }
    @get:Bindable
    var content: String = content
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }
    @get:Bindable
    var date: String = date
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }
}