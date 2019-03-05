package com.cjw.vettelgank.data.source

import com.cjw.vettelgank.data.Gank

interface SearchSource {

    interface SearchCallback {

        fun onSearchLoaded(gankList: List<Gank>, isEnd: Boolean)

        fun onDataNotAvailable()
    }

    interface Remote {

        fun search(queryText: String, page: Int, count: Int, callback: SearchCallback)

    }

    fun refreshSearch(queryText: String, callback: SearchCallback)

    fun loadMoreSearch(queryText: String, callback: SearchCallback)

}