package com.cjw.vettelgank.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cjw.vettelgank.Injection
import com.cjw.vettelgank.data.source.GankDailyRepository
import com.cjw.vettelgank.data.source.GankFilterRepository
import com.cjw.vettelgank.ui.home.daily.GankDailyViewModel

class ViewModelFactory private constructor(
    private val gankDailyRepository: GankDailyRepository,
    private val gankFilterRepository: GankFilterRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(GankDailyViewModel::class.java) ->{
                    Log.i("GankDailyFragment", "GankDailyViewModel create")
                    GankDailyViewModel(gankDailyRepository)
                }
                else ->
                    throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
            }

        } as T


    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideGankRepository(application),
                    Injection.provideGankFilterRepository()
                )
            }
    }
}