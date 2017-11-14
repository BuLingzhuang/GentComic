package com.bulingzhuang.gentcomic.utils

import android.support.v4.view.ViewPager
import android.view.View

/**
 * ================================================
 * 作    者：bulingzhuang
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/11/13
 * 描    述：重叠ViewPager处理方法
 * ================================================
 */
class ScrollOffsetTransformer(isFullScreen: Boolean) : ViewPager.PageTransformer {

    private var offset = -8f

    init {
        offset = if (isFullScreen) {
            -10f
        } else {
            -8f
        }
    }

    /**
     * position参数指明给定页面相对于屏幕中心的位置。它是一个动态属性，会随着页面的滚动而改变。
     * 当一个页面（page)填充整个屏幕时，positoin值为0；
     * 当一个页面（page)刚刚离开屏幕右(左）侧时，position值为1（-1）；
     * 当两个页面分别滚动到一半时，其中一个页面是-0.5，另一个页面是0.5。
     * 基于屏幕上页面的位置，通过诸如setAlpha()、setTranslationX()或setScaleY()方法来设置页面的属性，创建自定义的滑动动画。
     */
    override fun transformPage(page: View, position: Float) {
        //        if (position > 0) {
        //右侧的缓存页往左偏移
        page.translationX = offset * 24f * position
        page.alpha = (1 - Math.abs(position)) * 0.6f + 0.4f
        page.scaleY = (1 - Math.abs(position)) * 0.15f + 0.85f
        //        }
    }


}