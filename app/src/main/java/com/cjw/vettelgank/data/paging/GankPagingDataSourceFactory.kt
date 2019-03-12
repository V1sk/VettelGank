package com.cjw.vettelgank.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.cjw.vettelgank.Injection
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.api.GankService

class GankPagingDataSourceFactory(
    private val currentFiltering: String,
    private val gankService: GankService = Injection.provideGankService()
) : DataSource.Factory<Int, Gank>() {

    val sourceLiveData = MutableLiveData<GankPagingDataSource>()

    override fun create(): DataSource<Int, Gank> {
        val source = GankPagingDataSource(currentFiltering, gankService)
        sourceLiveData.postValue(source)
        return source
    }
}