package com.cjw.vettelgank.data.source.local

import com.cjw.vettelgank.data.source.GankFilterSource

class GankFilterLocalSource : GankFilterSource {
    override fun loadMoreGankList(currentFiltering: String, callback: GankFilterSource.LoadGankFilterCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshGankList(currentFiltering: String, callback: GankFilterSource.LoadGankFilterCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun gankFilter(filter: String, page: Int, count: Int, callback: GankFilterSource.LoadGankFilterCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private var INSTANCE: GankFilterLocalSource? = null

        @JvmStatic
        fun getInstance(): GankFilterLocalSource {
            return INSTANCE ?: GankFilterLocalSource().apply {
                INSTANCE = this
            }
        }
    }

}