package com.cjw.vettelgank.data.source.request

object ServerConfig {

    private const val host = "http://gank.io/api"

    const val gankDailyApi = "$host/today"

    fun gankDataFilterApi(filterType: String, page: Int, count: Int = 20): String {
        return "$host/data/$filterType/$count/$page"
    }

    fun searchApi(queryText: String, page: Int, count: Int=20): String {
        return "$host/search/query/$queryText/category/all/count/$count/page/$page"
    }

}