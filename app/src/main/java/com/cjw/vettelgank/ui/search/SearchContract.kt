package com.cjw.vettelgank.ui.search

import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.ui.BasePresenter
import com.cjw.vettelgank.ui.BaseView

interface SearchContract {

    interface View : BaseView<Presenter> {

        fun onRefreshSearchResult(gankList: List<Gank>, isEnd: Boolean)

        fun onLoadMoreSearchResult(gankList: List<Gank>, isEnd: Boolean)

        fun refreshError()

        fun loadMoreError()
    }

    interface Presenter : BasePresenter {

        var queryText: String

        fun refreshSearch()

        fun loadMoreSearch()
    }

}