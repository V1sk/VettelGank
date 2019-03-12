package com.cjw.vettelgank.data.source.remote

import com.cjw.vettelgank.Injection
import com.cjw.vettelgank.data.GankFilterResult
import com.cjw.vettelgank.data.api.GankService
import com.cjw.vettelgank.data.source.SearchSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRemoteSource private constructor(
    private val gankService: GankService = Injection.provideGankService()
): SearchSource.Remote {

    override fun search(queryText: String, page: Int, count: Int, callback: SearchSource.SearchCallback) {

        gankService.search(queryText, count, page).enqueue(object : Callback<GankFilterResult> {
            override fun onFailure(call: Call<GankFilterResult>, t: Throwable) {
                callback.onDataNotAvailable()
            }

            override fun onResponse(call: Call<GankFilterResult>, response: Response<GankFilterResult>) {
                if (response.isSuccessful) {
                    val searchResult = response.body()
                    if (searchResult == null || searchResult.error) {
                        callback.onDataNotAvailable()
                    } else {
                        callback.onSearchLoaded(searchResult.results, searchResult.results.size < count)
                    }
                } else {
                    callback.onDataNotAvailable()
                }
            }

        })

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