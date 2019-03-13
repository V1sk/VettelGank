package com.cjw.vettelgank.ui.home.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.cjw.vettelgank.data.paging.filter.GankFilterRepository

class GankFilterViewModel(private val gankFilterRepository: GankFilterRepository) : ViewModel() {

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

}