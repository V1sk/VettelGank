package com.cjw.vettelgank.data.source.remote

import com.cjw.vettelgank.data.GankDailyData
import com.cjw.vettelgank.data.GankDailyResult
import com.cjw.vettelgank.data.api.RetrofitClient
import com.cjw.vettelgank.data.source.GankDailySource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GankDailyRemoteSource private constructor() : GankDailySource {

    override fun gankDaily(callback: GankDailySource.LoadGankCallback) {

        RetrofitClient.INSTANCE.gankDaily().enqueue(object : Callback<GankDailyResult> {
            override fun onFailure(call: Call<GankDailyResult>, t: Throwable) {
                callback.onDataNotAvailable()
            }

            override fun onResponse(call: Call<GankDailyResult>, response: Response<GankDailyResult>) {
                if (response.isSuccessful) {
                    val gankDailyResult = response.body()
                    if (gankDailyResult == null || gankDailyResult.error) {
                        callback.onDataNotAvailable()
                    } else {
                        callback.onGankLoaded(gankDailyResult.results)
                    }
                } else {
                    callback.onDataNotAvailable()
                }
            }

        })
    }

    override fun deleteGankDaily() {
        // Not require on remote source
    }

    override fun saveGankDaily(gankDailyData: GankDailyData) {
        // Not require on remote source
    }

    companion object {
        private var INSTANCE: GankDailyRemoteSource? = null

        @JvmStatic
        fun getInstance(): GankDailyRemoteSource {
            return INSTANCE ?: GankDailyRemoteSource().apply {
                INSTANCE = this
            }
        }
    }

}