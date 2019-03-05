package com.cjw.vettelgank.ui.home.filter

import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.ui.BasePresenter
import com.cjw.vettelgank.ui.BaseView

interface GankFilterContract {

    interface View : BaseView<Presenter> {

        fun onRefresh(gankList: List<Gank>, isEnd: Boolean)

        fun onLoadMore(gankList: List<Gank>, isEnd: Boolean)

        fun refreshGankError()

        fun loadMoreGankError()
    }

    interface Presenter : BasePresenter {

        var currentFiltering: String

        fun refresh()

        fun loadMore()
    }

}