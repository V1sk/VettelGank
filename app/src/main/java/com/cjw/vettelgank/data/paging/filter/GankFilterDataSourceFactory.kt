package com.cjw.vettelgank.data.paging.filter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.cjw.vettelgank.Injection
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.api.GankService

class GankFilterDataSourceFactory(
    private val currentFiltering: String,
    private val gankService: GankService = Injection.provideGankService()
) : DataSource.Factory<Int, Gank>() {

    val sourceLiveData = MutableLiveData<GankFilterDataSource>()

    override fun create(): DataSource<Int, Gank> {
        val source = GankFilterDataSource(currentFiltering, gankService)
        sourceLiveData.postValue(source)
        return source
    }
}