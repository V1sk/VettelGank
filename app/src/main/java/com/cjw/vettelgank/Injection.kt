package com.cjw.vettelgank

import android.content.Context
import com.cjw.vettelgank.data.api.GankService
import com.cjw.vettelgank.data.api.RetrofitClient
import com.cjw.vettelgank.data.paging.filter.GankFilterRepository
import com.cjw.vettelgank.data.paging.search.GankSearchRepository
import com.cjw.vettelgank.data.source.GankDailyRepository
import com.cjw.vettelgank.data.source.local.GankDailyLocalSource
import com.cjw.vettelgank.data.source.local.GankDatabase
import com.cjw.vettelgank.data.source.remote.GankDailyRemoteSource
import com.cjw.vettelgank.util.AppExecutors

object Injection {
    fun provideGankRepository(context: Context): GankDailyRepository {
        val database = GankDatabase.getInstance(context)
        return GankDailyRepository.getInstance(
            GankDailyRemoteSource.getInstance(),
            GankDailyLocalSource.getInstance(AppExecutors(), database.gankDao())
        )
    }

    fun provideGankPagingRepository(): GankFilterRepository {
        return GankFilterRepository.getInstance()
    }

    fun provideSearchRepository(): GankSearchRepository {
        return GankSearchRepository.getInstance()
    }

    fun provideGankService(): GankService {
        return RetrofitClient.INSTANCE
    }
}