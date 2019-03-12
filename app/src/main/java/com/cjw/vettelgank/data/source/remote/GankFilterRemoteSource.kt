package com.cjw.vettelgank.data.source.remote

import com.cjw.vettelgank.Injection
import com.cjw.vettelgank.data.GankFilterResult
import com.cjw.vettelgank.data.api.GankService
import com.cjw.vettelgank.data.source.GankFilterSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GankFilterRemoteSource private constructor(
    private val gankService: GankService = Injection.provideGankService()
): GankFilterSource {

    override fun gankFilter(filter: String, page: Int, count: Int, callback: GankFilterSource.LoadGankFilterCallback) {

        gankService.gankFilter(filter, count, page).enqueue(object : Callback<GankFilterResult> {
            override fun onFailure(call: Call<GankFilterResult>, t: Throwable) {
                callback.onDataNotAvailable()
            }

            override fun onResponse(call: Call<GankFilterResult>, response: Response<GankFilterResult>) {
                if (response.isSuccessful) {
                    val filterResult = response.body()
                    if (filterResult == null || filterResult.error) {
                        callback.onDataNotAvailable()
                    } else {
                        callback.onGankFilterLoaded(filterResult.results, filterResult.results.size < count)
                    }
                } else {
                    callback.onDataNotAvailable()
                }
            }

        })
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