package com.cjw.vettelgank.data.source

import com.cjw.vettelgank.data.GankDailyData
import com.cjw.vettelgank.data.source.local.GankDailyLocalSource
import com.cjw.vettelgank.data.source.remote.GankDailyRemoteSource

class GankDailyRepository private constructor(
    private val gankDailyRemoteSource: GankDailyRemoteSource,
    private val gankDailyLocalSource: GankDailyLocalSource
) : GankDailySource {

    private var cacheDailyData: GankDailyData? = null // memory cache
    private var cacheIsDirty = false

    override fun refreshGank() {
        cacheIsDirty = true
    }

    override fun gankDaily(callback: GankDailySource.LoadGankCallback) {
        if (cacheDailyData == null) {
            // no memory cache, try to load db cache first
            getLocalGankDaily(callback)
        } else {
            if (!cacheIsDirty) {
                callback.onGankLoaded(cacheDailyData!!)
                return
            }
        }

        if (cacheIsDirty) {
            getRemoteGankDaily(callback)
        } else {
            getLocalGankDaily(callback)
        }


    }

    private fun getLocalGankDaily(callback: GankDailySource.LoadGankCallback) {
        gankDailyLocalSource.gankDaily(object : GankDailySource.LoadGankCallback {
            override fun onGankLoaded(gankDailyData: GankDailyData) {
                refreshCache(gankDailyData)
                callback.onGankLoaded(gankDailyData)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    private fun getRemoteGankDaily(callback: GankDailySource.LoadGankCallback) {
        gankDailyRemoteSource.gankDaily(object : GankDailySource.LoadGankCallback {

            override fun onGankLoaded(gankDailyData: GankDailyData) {
                refreshCache(gankDailyData)
                saveGankDaily(gankDailyData)
                callback.onGankLoaded(gankDailyData)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    private fun refreshCache(gankDailyData: GankDailyData) {
        cacheDailyData = gankDailyData
        cacheIsDirty = false
    }

    override fun deleteGankDaily() {
        gankDailyLocalSource.deleteGankDaily()
    }

    override fun saveGankDaily(gankDailyData: GankDailyData) {
        gankDailyLocalSource.saveGankDaily(gankDailyData)
    }

    companion object {
        private var INSTANCE: GankDailyRepository? = null

        @JvmStatic
        fun getInstance(
            gankDataRemoteSource: GankDailyRemoteSource,
            gankDataLocalSource: GankDailyLocalSource
        ): GankDailyRepository {
            return INSTANCE ?: GankDailyRepository(gankDataRemoteSource, gankDataLocalSource).apply {
                INSTANCE = this
            }
        }
    }

}