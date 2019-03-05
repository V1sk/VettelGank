package com.cjw.vettelgank.data.source

import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.source.remote.SearchRemoteSource

class SearchRepository(private val searchRemoteSource: SearchRemoteSource) : SearchSource {

    private val pageSize = 20
    private var currentPage = 1

    override fun refreshSearch(queryText: String, callback: SearchSource.SearchCallback) {
        currentPage = 1
        searchRemoteSource.search(queryText, 1, pageSize, callback)
    }

    override fun loadMoreSearch(queryText: String, callback: SearchSource.SearchCallback) {

        searchRemoteSource.search(queryText, currentPage + 1, pageSize, object : SearchSource.SearchCallback {
            override fun onSearchLoaded(gankList: List<Gank>, isEnd: Boolean) {
                currentPage++
                callback.onSearchLoaded(gankList, isEnd)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    companion object {
        private var INSTANCE: SearchRepository? = null

        @JvmStatic
        fun getInstance(searchRemoteSource: SearchRemoteSource): SearchRepository {
            return INSTANCE ?: SearchRepository(searchRemoteSource).apply {
                INSTANCE = this
            }
        }
    }
}