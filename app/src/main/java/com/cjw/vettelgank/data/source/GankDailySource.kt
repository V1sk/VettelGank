package com.cjw.vettelgank.data.source

import com.cjw.vettelgank.data.GankDailyData

interface GankDailySource {

    interface LoadGankCallback {

        fun onGankLoaded(gankDailyData: GankDailyData)

        fun onDataNotAvailable()
    }

    fun gankDaily(callback: LoadGankCallback)

    fun deleteGankDaily()

    fun saveGankDaily(gankDailyData: GankDailyData)

}