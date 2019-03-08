package com.cjw.vettelgank.data.source.remote

import android.util.Log
import com.cjw.vettelgank.data.GankDailyData
import com.cjw.vettelgank.data.source.GankDailySource
import com.cjw.vettelgank.data.source.request.GankDailyRequest
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class GankDailyRemoteSource private constructor() : GankDailySource {

    private val tag = "GankDailyRemoteSource"

    override fun gankDaily(callback: GankDailySource.LoadGankCallback) {
        doAsync {
            val gankResult = GankDailyRequest().request()
            Log.i(tag, "$gankResult")
            uiThread {
                if (gankResult == null) {
                    callback.onDataNotAvailable()
                } else {
                    if (gankResult.error) {
                        callback.onDataNotAvailable()
                    } else {
                        callback.onGankLoaded(gankResult.results)
                    }
                }
            }

        }
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