package com.cjw.vettelgank.ui.home.daily

import com.cjw.vettelgank.data.GankDailyData
import com.cjw.vettelgank.data.source.GankDailyRepository
import com.cjw.vettelgank.data.source.GankDailySource

class GankDailyPresenter(
    private val gankDailyRepository: GankDailyRepository,
    var gankDailyView: GankDailyContract.View?
) :
    GankDailyContract.Presenter {

    private var firstLoad = true

    init {
        gankDailyView?.presenter = this
    }

    override fun gankDaily(forceUpdate: Boolean) {
        if (forceUpdate || firstLoad) {
            gankDailyRepository.refreshGank()
        }
        firstLoad = false
        gankDailyRepository.gankDaily(object : GankDailySource.LoadGankCallback {

            override fun onGankLoaded(gankDailyData: GankDailyData) {
                gankDailyView?.showGankDaily(gankDailyData)
            }

            override fun onDataNotAvailable() {
                gankDailyView?.showLoadingGankError()
            }

        })


    }

    override fun start() {
        gankDaily(false)
    }
}