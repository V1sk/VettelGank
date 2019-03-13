package com.cjw.vettelgank.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.cjw.vettelgank.data.paging.search.GankSearchRepository

class SearchViewModel(private val searchRepository: GankSearchRepository) : ViewModel() {

    private val queryText = MutableLiveData<String>()
    private val searchResult = map(queryText) {
        searchRepository.search(it)
    }

    val gankList = switchMap(searchResult) {
        it.pagedList
    }
    val networkState = switchMap(searchResult) {
        it.networkState
    }
    val refreshState = switchMap(searchResult) {
        it.refreshState
    }

    fun refresh() {
        searchResult.value?.refresh?.invoke()
    }

    fun retry() {
        searchResult.value?.retry?.invoke()
    }

    fun search(query: String) {
        if (query == queryText.value)
            return
        queryText.value = query
    }

}