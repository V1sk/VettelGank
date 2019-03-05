package com.cjw.vettelgank.ui.search

import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.source.SearchRepository
import com.cjw.vettelgank.data.source.SearchSource

class SearchPresenter(
    private val searchRepository: SearchRepository,
    private val searchView: SearchContract.View
) : SearchContract.Presenter {

    override var queryText: String = ""
        set(value) {
            field = value
            refreshSearch()
        }

    override fun refreshSearch() {
        searchRepository.refreshSearch(queryText, refreshCallback)
    }

    private val refreshCallback = object : SearchSource.SearchCallback {
        override fun onSearchLoaded(gankList: List<Gank>, isEnd: Boolean) {
            searchView.onRefreshSearchResult(gankList, isEnd)
        }

        override fun onDataNotAvailable() {
            searchView.refreshError()
        }

    }

    override fun loadMoreSearch() {
        searchRepository.loadMoreSearch(queryText, loadMoreCallback)
    }

    private val loadMoreCallback = object : SearchSource.SearchCallback {
        override fun onSearchLoaded(gankList: List<Gank>, isEnd: Boolean) {
            searchView.onLoadMoreSearchResult(gankList, isEnd)
        }

        override fun onDataNotAvailable() {
            searchView.loadMoreError()
        }

    }

    override fun start() {

    }
}