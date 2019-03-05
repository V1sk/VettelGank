package com.cjw.vettelgank.data.source.local

import com.cjw.vettelgank.data.GankDailyData
import com.cjw.vettelgank.data.source.GankDailySource
import com.cjw.vettelgank.util.AppExecutors

class GankDailyLocalSource private constructor(
    private val appExecutors: AppExecutors,
    private val gankDao: GankDao
) : GankDailySource {

    override fun deleteGankDaily() {
        appExecutors.diskIO.execute {
            gankDao.deleteDaily()
        }
    }

    override fun saveGankDaily(gankDailyData: GankDailyData) {
        appExecutors.diskIO.execute {
            gankDao.deleteDaily()
            gankDao.insert(gankDailyData)
        }
    }

    override fun refreshGank() {

    }

    override fun gankDaily(callback: GankDailySource.LoadGankCallback) {
        appExecutors.diskIO.execute {
            val dailyData = gankDao.loadDaily()
            appExecutors.mainThread.execute {
                if (dailyData != null) {
                    callback.onGankLoaded(dailyData)
                }
            }
        }
    }

    companion object {
        private var INSTANCE: GankDailyLocalSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, gankDao: GankDao): GankDailyLocalSource {
            return INSTANCE ?: GankDailyLocalSource(appExecutors, gankDao).apply {
                INSTANCE = this
            }
        }
    }

}