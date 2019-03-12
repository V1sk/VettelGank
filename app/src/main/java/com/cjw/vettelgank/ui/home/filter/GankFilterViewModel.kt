package com.cjw.vettelgank.ui.home.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.cjw.vettelgank.data.paging.GankPagingRepository

class GankFilterViewModel(private val gankFilterRepository: GankPagingRepository) : ViewModel() {

    private val currentFiltering = MutableLiveData<String>()
    private val gankResult = map(currentFiltering) {
        gankFilterRepository.gankFilter(it)
    }
    val gankList = switchMap(gankResult) {
        it.pagedList
    }
    val networkState = switchMap(gankResult) {
        it.networkState
    }
    val refreshState = switchMap(gankResult) {
        it.refreshState
    }

    fun refresh() {
        gankResult.value?.refresh?.invoke()
    }

    fun retry() {
        gankResult.value?.retry?.invoke()
    }

    fun filter(filtering: String) {
        if (currentFiltering.value == filtering)
            return
        currentFiltering.value = filtering
    }

    fun currentFiltering(): String? {
        return currentFiltering.value
    }

//    private val _refreshing = MutableLiveData<Boolean>()
//    val refreshing: LiveData<Boolean>
//        get() = _refreshing

//    private val _loadMoreState = MutableLiveData<MutableMap<String, Int>>()
//    val loadMoreState: LiveData<MutableMap<String, Int>>
//        get() = _loadMoreState

//    private val _data = MutableLiveData<MutableMap<String, MutableList<Gank>>>()
//    val data: LiveData<MutableMap<String, MutableList<Gank>>>
//        get() = _data

//    private val _netWorkError = MutableLiveData<Boolean>()
//    val netWorkError: LiveData<Boolean>
//        get() = _netWorkError


//    fun loadMore() {
//        gankFilterRepository.loadMoreGankList(currentFiltering, loadMoreCallback)
//    }

//    private val onRefreshCallback = object : GankFilterSource.LoadGankFilterCallback {
//        override fun onGankFilterLoaded(gankList: List<Gank>, isEnd: Boolean) {
//            _refreshing.value = false
//            val map = _data.value ?: mutableMapOf()
//            map[currentFiltering] = gankList.toMutableList()
//            _data.value = map
//
//            setLoadMoreState(if (isEnd) LoadingHolder.STATUS_END else LoadingHolder.STATUS_LOADING)
//        }
//
//        override fun onDataNotAvailable() {
//            _refreshing.value = false
//            _netWorkError.value = true
//        }
//    }
//
//    private val loadMoreCallback = object : GankFilterSource.LoadGankFilterCallback {
//
//        override fun onGankFilterLoaded(gankList: List<Gank>, isEnd: Boolean) {
//            val map = _data.value
//            map!![currentFiltering]?.addAll(gankList)
//            _data.value = map
//            setLoadMoreState(if (isEnd) LoadingHolder.STATUS_END else LoadingHolder.STATUS_LOADING)
//        }
//
//        override fun onDataNotAvailable() {
//            setLoadMoreState(LoadingHolder.STATUS_FAILED)
//        }
//
//    }
//
//    private fun setLoadMoreState(state: Int) {
//        val map = _loadMoreState.value ?: mutableMapOf()
//        map[currentFiltering] = state
//        _loadMoreState.value = map
//    }

}