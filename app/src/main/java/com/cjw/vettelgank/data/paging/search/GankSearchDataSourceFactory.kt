package com.cjw.vettelgank.data.paging.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.cjw.vettelgank.Injection
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.api.GankService

class GankSearchDataSourceFactory(
    private val queryText: String,
    private val gankService: GankService = Injection.provideGankService()
) : DataSource.Factory<Int, Gank>() {

    val sourceLiveData = MutableLiveData<GankSearchDataSource>()

    override fun create(): DataSource<Int, Gank> {
        val source = GankSearchDataSource(queryText, gankService)
        sourceLiveData.postValue(source)
        return source
    }
}