package com.cjw.vettelgank.data.source.remote

import com.cjw.vettelgank.data.source.SearchSource
import com.cjw.vettelgank.data.source.request.SearchRequest
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchRemoteSource : SearchSource.Remote {

    override fun search(queryText: String, page: Int, count: Int, callback: SearchSource.SearchCallback) {
        doAsync {
            val searchResult = SearchRequest(queryText, page, count).request()
            uiThread {
                if (searchResult == null) {
                    callback.onDataNotAvailable()
                    return@uiThread
                }

                if (searchResult.error) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onSearchLoaded(searchResult.results, searchResult.results.size < count)
                }
            }
        }
    }

    companion object {
        private var INSTANCE: SearchRemoteSource? = null

        @JvmStatic
        fun getInstance(): SearchRemoteSource {
            return INSTANCE ?: SearchRemoteSource().apply {
                INSTANCE = this
            }
        }
    }

}