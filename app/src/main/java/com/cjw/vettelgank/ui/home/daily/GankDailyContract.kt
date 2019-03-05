package com.cjw.vettelgank.ui.home.daily

import com.cjw.vettelgank.data.GankDailyData
import com.cjw.vettelgank.ui.BasePresenter
import com.cjw.vettelgank.ui.BaseView

interface GankDailyContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showGankDaily(gankDailyData: GankDailyData)

        fun showLoadingGankError()
    }

    interface Presenter : BasePresenter {

        fun gankDaily(forceUpdate: Boolean)
    }

}