package com.cjw.vettelgank.data.paging.filter

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.cjw.vettelgank.Injection
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.GankFilterResult
import com.cjw.vettelgank.data.api.GankService
import com.cjw.vettelgank.data.paging.NetworkState
import retrofit2.Call
import retrofit2.Response

class GankFilterDataSource(
    private val currentFiltering: String,
    private val gankService: GankService = Injection.provideGankService()
) : PageKeyedDataSource<Int, Gank>() {

    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.also {
            it.invoke()
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Gank>) {
        initialLoad.postValue(NetworkState.LOADING)
        gankService.gankFilter(currentFiltering, params.requestedLoadSize, 1)
            .enqueue(object : retrofit2.Callback<GankFilterResult> {

                override fun onFailure(call: Call<GankFilterResult>, t: Throwable) {
                    retry = {
                        loadInitial(params, callback)
                    }
                    initialLoad.postValue(NetworkState.FAILED)
                }

                override fun onResponse(call: Call<GankFilterResult>, response: Response<GankFilterResult>) {
                    if (response.isSuccessful) {
                        retry = null
                        callback.onResult(
                            response.body()?.results ?: emptyList(),
                            null, 2
                        )
                        initialLoad.postValue(NetworkState.LOADED)
                    } else {
                        retry = {
                            loadInitial(params, callback)
                        }
                        initialLoad.postValue(NetworkState.FAILED)
                    }
                }

            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Gank>) {
        networkState.postValue(NetworkState.LOADING)
        gankService.gankFilter(currentFiltering, params.requestedLoadSize, params.key)
            .enqueue(object : retrofit2.Callback<GankFilterResult> {
                override fun onFailure(call: Call<GankFilterResult>, t: Throwable) {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.FAILED)
                }

                override fun onResponse(call: Call<GankFilterResult>, response: Response<GankFilterResult>) {
                    if (response.isSuccessful) {
                        retry = null
                        callback.onResult(
                            response.body()?.results ?: emptyList(),
                            params.key + 1
                        )
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(NetworkState.FAILED)
                    }
                }

            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Gank>) {

    }

}