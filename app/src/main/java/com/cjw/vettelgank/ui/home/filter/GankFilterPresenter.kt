package com.cjw.vettelgank.ui.home.filter

import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.source.GankFilterRepository
import com.cjw.vettelgank.data.source.GankFilterSource
import com.cjw.vettelgank.ui.home.GankFilterType

class GankFilterPresenter(
    private val gankFilterRepository: GankFilterRepository,
    var gankFilterView: GankFilterContract.View?
) : GankFilterContract.Presenter {

    override var currentFiltering: String = GankFilterType.ANDROID

    override fun refresh() {
        gankFilterRepository.refreshGankList(currentFiltering, refreshCallback)
    }

    override fun loadMore() {
        gankFilterRepository.loadMoreGankList(currentFiltering, loadMoreCallback)
    }

    override fun start() {
        refresh()
    }

    private val refreshCallback = object : GankFilterSource.LoadGankFilterCallback {
        override fun onGankFilterLoaded(gankList: List<Gank>, isEnd: Boolean) {
            gankFilterView?.onRefresh(gankList, isEnd)
        }

        override fun onDataNotAvailable() {
            gankFilterView?.refreshGankError()
        }

    }

    private val loadMoreCallback = object : GankFilterSource.LoadGankFilterCallback {
        override fun onGankFilterLoaded(gankList: List<Gank>, isEnd: Boolean) {
            gankFilterView?.onLoadMore(gankList, isEnd)
        }

        override fun onDataNotAvailable() {
            gankFilterView?.loadMoreGankError()
        }

    }


}