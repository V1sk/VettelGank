package com.cjw.vettelgank.data.paging

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cjw.vettelgank.data.Gank

class GankPagingRepository {

    companion object {
        private const val PAGE_SIZE = 30
        private var INSTANCE: GankPagingRepository? = null

        @JvmStatic
        fun getInstance(): GankPagingRepository {
            return INSTANCE ?: GankPagingRepository().apply {
                INSTANCE = this
            }
        }
    }

    fun gankFilter(currentFiltering: String): Listing<Gank> {

        val sourceFactory = GankPagingDataSourceFactory(currentFiltering)

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