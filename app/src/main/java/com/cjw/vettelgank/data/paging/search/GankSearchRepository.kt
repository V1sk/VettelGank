package com.cjw.vettelgank.data.paging.search

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.paging.Listing

class GankSearchRepository {

    companion object {
        private const val PAGE_SIZE = 30
        private var INSTANCE: GankSearchRepository? = null

        @JvmStatic
        fun getInstance(): GankSearchRepository {
            return INSTANCE
                ?: GankSearchRepository().apply {
                    INSTANCE = this
                }
        }
    }

    fun search(queryText: String): Listing<Gank> {
        val sourceFactory = GankSearchDataSourceFactory(queryText)
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()

        val livePagedList = LivePagedListBuilder<Int, Gank>(sourceFactory, config).build()
        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }

}