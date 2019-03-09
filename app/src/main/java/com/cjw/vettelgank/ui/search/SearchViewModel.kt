package com.cjw.vettelgank.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.source.SearchRepository
import com.cjw.vettelgank.data.source.SearchSource
import com.cjw.vettelgank.ui.adapter.holder.LoadingHolder

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    var queryText: String = ""
        set(value) {
            field = value
            refresh()
        }

    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    private val _loadMoreState = MutableLiveData<Int>()
    val loadMoreState: LiveData<Int>
        get() = _loadMoreState

    private val _data = MutableLiveData<MutableList<Gank>>()
    val data: LiveData<MutableList<Gank>>
        get() = _data

    private val _netWorkError = MutableLiveData<Boolean>()
    val netWorkError: LiveData<Boolean>
        get() = _netWorkError

    fun refresh() {
        _refreshing.value = true
        searchRepository.refreshSearch(queryText, object : SearchSource.SearchCallback {
            override fun onSearchLoaded(gankList: List<Gank>, isEnd: Boolean) {
                _refreshing.value = false
                _data.value = gankList.toMutableList()
                _loadMoreState.value = if (isEnd) LoadingHolder.STATUS_END else LoadingHolder.STATUS_LOADING
            }

            override fun onDataNotAvailable() {
                _refreshing.value = false
            }

        })
    }

    fun loadMore() {
        searchRepository.loadMoreSearch(queryText, object : SearchSource.SearchCallback {

            override fun onSearchLoaded(gankList: List<Gank>, isEnd: Boolean) {
                val list = _data.value ?: mutableListOf()
                list.addAll(gankList)
                _data.value = list
                _loadMoreState.value = if (isEnd) LoadingHolder.STATUS_END else LoadingHolder.STATUS_LOADING
            }

            override fun onDataNotAvailable() {
                _loadMoreState.value = LoadingHolder.STATUS_FAILED
            }

        })
    }

}