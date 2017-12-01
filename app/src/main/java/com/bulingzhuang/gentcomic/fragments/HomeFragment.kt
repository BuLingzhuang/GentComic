package com.bulingzhuang.gentcomic.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bulingzhuang.gentcomic.R
import com.bulingzhuang.gentcomic.base.BaseFragment
import com.bulingzhuang.gentcomic.entity.WeatherData
import com.bulingzhuang.gentcomic.impl.presenters.MainHomePresenterImpl
import com.bulingzhuang.gentcomic.interfaces.presenters.MainHomePresenter
import com.bulingzhuang.gentcomic.interfaces.views.MainHomeView
import com.bulingzhuang.gentcomic.utils.showSnakeBar
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * ================================================
 * 作    者：bulingzhuang
 * 版    本：1.0
 * 邮    箱：bulingzhuang@foxmail.com
 * 创建日期：2017/12/1
 * 描    述：HomeFragment
 * ================================================
 */
class HomeFragment : BaseFragment(), MainHomeView {

    companion object {
        fun newInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.title = title
            args.putString(TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mPresenter: MainHomePresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        mPresenter.getMainHomeListData(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    private fun init() {
        mPresenter = MainHomePresenterImpl(this)
        mPresenter.initAdapter(activity, rv_content)

        srl_content.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        srl_content.setOnRefreshListener {
            mPresenter.getMainHomeListData(context)
        }
    }

    override fun showSnakeBar(msg: String) {
        context.showSnakeBar(msg, cl_gen)
    }

    override fun setRefreshing(refreshing: Boolean) {
        srl_content.isRefreshing = refreshing
    }

    override fun updateWeather(data: WeatherData.ResultsBean.NowBean, showAnim: Boolean) {
    }
}
