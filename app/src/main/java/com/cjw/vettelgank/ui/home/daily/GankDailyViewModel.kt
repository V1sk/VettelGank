package com.cjw.vettelgank.ui.home.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjw.vettelgank.data.GankDailyData
import com.cjw.vettelgank.data.source.GankDailyRepository
import com.cjw.vettelgank.data.source.GankDailySource
import com.cjw.vettelgank.data.ui.GankItem

class GankDailyViewModel(private val gankDailyRepository: GankDailyRepository) : ViewModel() {

    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    private val _gankItemList = MutableLiveData<MutableList<GankItem>>()
    val gankItemList: LiveData<MutableList<GankItem>>
        get() = _gankItemList

    private val _netWorkError = MutableLiveData<Boolean>()
    val netWorkError: LiveData<Boolean>
        get() = _netWorkError


    fun start() {
        gankDaily()
    }

    private fun gankDaily() {
        _refreshing.value = true
        gankDailyRepository.gankDaily(dataSourceCallback)

    }

    private val dataSourceCallback = object : GankDailySource.LoadGankCallback {

        override fun onGankLoaded(gankDailyData: GankDailyData) {
            _refreshing.value = false
            _gankItemList.value = gankDailyData.toGankUIItem()
        }

        override fun onDataNotAvailable() {
            _refreshing.value = false
            _netWorkError.value = true
        }

    }
}