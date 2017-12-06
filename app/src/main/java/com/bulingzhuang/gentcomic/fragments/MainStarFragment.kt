package com.bulingzhuang.gentcomic.fragments


import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.base.BaseFragment
import com.bulingzhuang.gentcomic.impl.presenters.MainStarPresenterImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.MainStarPresenter
import com.bulingzhuang.gentcomic.interfaces.views.MainStarView
import kotlinx.android.synthetic.main.fragment_main_star.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 版    本：1.0
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/4
 * 描    述：收藏Fragment
 * ================================================
 */
class MainStarFragment : BaseFragment(), MainStarView {

    companion object {
        fun newInstance(title: String): MainStarFragment {
            val fragment = MainStarFragment()
            val args = Bundle()
            fragment.title = title
            args.putString(TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mPresenter: MainStarPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main_star, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        mPresenter.initStarAdapter(rv_content)
    }

    private fun init() {
        mPresenter = MainStarPresenterImpl(this, activity)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.refreshStarList()
    }

    /**
     * 收藏列表数据是否为空
     */
    override fun hasStarData(hasData: Boolean) {
        TransitionManager.beginDelayedTransition(cl_gen, Fade())
        if (hasData) {
            rv_content.visibility = View.GONE
            iv_none.visibility = View.VISIBLE
        } else {
            iv_none.visibility = View.GONE
            rv_content.visibility = View.VISIBLE
        }
    }
}
