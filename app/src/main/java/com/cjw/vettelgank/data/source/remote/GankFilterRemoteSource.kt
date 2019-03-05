package com.cjw.vettelgank.data.source.remote

import com.cjw.vettelgank.data.source.GankFilterSource
import com.cjw.vettelgank.data.source.request.GankFilterRequest
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class GankFilterRemoteSource : GankFilterSource {

    override fun gankFilter(filter: String, page: Int, count: Int, callback: GankFilterSource.LoadGankFilterCallback) {
        doAsync {
            val gankFilterResult = GankFilterRequest(filter, page, count).request()
            uiThread {
                if (gankFilterResult == null) {
                    callback.onDataNotAvailable()
                    return@uiThread
                }

                if (gankFilterResult.error) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onGankFilterLoaded(gankFilterResult.results, gankFilterResult.results.size < count)
                }
            }
        }
    }

    override fun refreshGankList(currentFiltering: String, callback: GankFilterSource.LoadGankFilterCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadMoreGankList(currentFiltering: String, callback: GankFilterSource.LoadGankFilterCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private var INSTANCE: GankFilterRemoteSource? = null

        @JvmStatic
        fun getInstance(): GankFilterRemoteSource {
            return INSTANCE ?: GankFilterRemoteSource().apply {
                INSTANCE = this
            }
        }
    }

}