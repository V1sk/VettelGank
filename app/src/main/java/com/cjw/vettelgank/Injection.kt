package com.cjw.vettelgank

import android.content.Context
import com.cjw.vettelgank.data.api.GankService
import com.cjw.vettelgank.data.api.RetrofitClient
import com.cjw.vettelgank.data.paging.GankPagingRepository
import com.cjw.vettelgank.data.source.GankDailyRepository
import com.cjw.vettelgank.data.source.SearchRepository
import com.cjw.vettelgank.data.source.local.GankDailyLocalSource
import com.cjw.vettelgank.data.source.local.GankDatabase
import com.cjw.vettelgank.data.source.remote.GankDailyRemoteSource
import com.cjw.vettelgank.data.source.remote.SearchRemoteSource
import com.cjw.vettelgank.util.AppExecutors

object Injection {
    fun provideGankRepository(context: Context): GankDailyRepository {
        val database = GankDatabase.getInstance(context)
        return GankDailyRepository.getInstance(
            GankDailyRemoteSource.getInstance(),
            GankDailyLocalSource.getInstance(AppExecutors(), database.gankDao())
        )
    }

//    fun provideGankFilterRepository(): GankFilterRepository {
//        return GankFilterRepository.getInstance(
//            GankFilterRemoteSource.getInstance(),
//            GankFilterLocalSource.getInstance()
//        )
//    }

    fun provideGankPagingRepository(): GankPagingRepository {
        return GankPagingRepository.getInstance()
    }

    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(SearchRemoteSource.getInstance())
    }

    fun provideGankService(): GankService {
        return RetrofitClient.INSTANCE
    }
}