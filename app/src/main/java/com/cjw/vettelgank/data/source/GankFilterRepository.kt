package com.cjw.vettelgank.data.source

import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.source.local.GankFilterLocalSource
import com.cjw.vettelgank.data.source.remote.GankFilterRemoteSource

class GankFilterRepository(
    private val gankFilterRemoteSource: GankFilterRemoteSource,
    private val gankFilterLocalSource: GankFilterLocalSource
) : GankFilterSource {

    private val pageSize = 20
    private var cachePageInfo: LinkedHashMap<String, Int> = LinkedHashMap()

    override fun refreshGankList(currentFiltering: String, callback: GankFilterSource.LoadGankFilterCallback) {
        cachePageInfo[currentFiltering] = 1
        gankFilter(currentFiltering, 1, pageSize, callback)
    }

    override fun loadMoreGankList(currentFiltering: String, callback: GankFilterSource.LoadGankFilterCallback) {
        val currentPage = cachePageInfo[currentFiltering] ?: 1

        gankFilter(currentFiltering, (currentPage + 1), pageSize, object : GankFilterSource.LoadGankFilterCallback {
            override fun onGankFilterLoaded(gankList: List<Gank>, isEnd: Boolean) {
                // page count += 1
                cachePageInfo[currentFiltering] = (cachePageInfo[currentFiltering] ?: 1) + 1
                callback.onGankFilterLoaded(gankList, isEnd)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    override fun gankFilter(filter: String, page: Int, count: Int, callback: GankFilterSource.LoadGankFilterCallback) {
        gankFilterRemoteSource.gankFilter(filter, page, count, callback)
    }

    companion object {
        private var INSTANCE: GankFilterRepository? = null

        @JvmStatic
        fun getInstance(
            gankFilterRemoteSource: GankFilterRemoteSource,
            gankFilterLocalSource: GankFilterLocalSource
        ): GankFilterRepository {
            return INSTANCE ?: GankFilterRepository(gankFilterRemoteSource, gankFilterLocalSource).apply {
                INSTANCE = this
            }
        }
    }

}