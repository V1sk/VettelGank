package com.cjw.vettelgank.data.source

import com.cjw.vettelgank.data.Gank

interface GankFilterSource {

    interface LoadGankFilterCallback {

        fun onGankFilterLoaded(gankList: List<Gank>, isEnd: Boolean)

        fun onDataNotAvailable()
    }

    fun gankFilter(filter: String, page: Int, count: Int, callback: LoadGankFilterCallback)

    fun refreshGankList(currentFiltering: String, callback: LoadGankFilterCallback)

    fun loadMoreGankList(currentFiltering: String, callback: LoadGankFilterCallback)


}