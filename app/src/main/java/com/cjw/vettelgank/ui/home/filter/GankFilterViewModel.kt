package com.cjw.vettelgank.ui.home.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjw.vettelgank.data.Gank
import com.cjw.vettelgank.data.source.GankFilterRepository
import com.cjw.vettelgank.data.source.GankFilterSource
import com.cjw.vettelgank.ui.adapter.holder.LoadingHolder
import com.cjw.vettelgank.ui.home.GankFilterType

class GankFilterViewModel(private val gankFilterRepository: GankFilterRepository) : ViewModel() {

    var currentFiltering = GankFilterType.ANDROID

    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    private val _loadMoreState = MutableLiveData<MutableMap<String, Int>>()
    val loadMoreState: LiveData<MutableMap<String, Int>>
        get() = _loadMoreState

    private val _data = MutableLiveData<MutableMap<String, MutableList<Gank>>>()
    val data: LiveData<MutableMap<String, MutableList<Gank>>>
        get() = _data

    private val _netWorkError = MutableLiveData<Boolean>()
    val netWorkError: LiveData<Boolean>
        get() = _netWorkError

    fun refresh() {
        _refreshing.value = true
        gankFilterRepository.refreshGankList(currentFiltering, onRefreshCallback)
    }

    fun loadMore() {
        gankFilterRepository.loadMoreGankList(currentFiltering, loadMoreCallback)
    }

    private val onRefreshCallback = object : GankFilterSource.LoadGankFilterCallback {
        override fun onGankFilterLoaded(gankList: List<Gank>, isEnd: Boolean) {
            _refreshing.value = false
            val map = _data.value ?: mutableMapOf()
            map[currentFiltering] = gankList.toMutableList()
            _data.value = map

            setLoadMoreState(if (isEnd) LoadingHolder.STATUS_END else LoadingHolder.STATUS_LOADING)
        }

        override fun onDataNotAvailable() {
            _refreshing.value = false
            _netWorkError.value = true
        }
    }

    private val loadMoreCallback = object : GankFilterSource.LoadGankFilterCallback {

        override fun onGankFilterLoaded(gankList: List<Gank>, isEnd: Boolean) {
            val map = _data.value
            map!![currentFiltering]?.addAll(gankList)
            _data.value = map
            setLoadMoreState(if (isEnd) LoadingHolder.STATUS_END else LoadingHolder.STATUS_LOADING)
        }

        override fun onDataNotAvailable() {
            setLoadMoreState(LoadingHolder.STATUS_FAILED)
        }

    }

    private fun setLoadMoreState(state: Int) {
        val map = _loadMoreState.value ?: mutableMapOf()
        map[currentFiltering] = state
        _loadMoreState.value = map
    }

}