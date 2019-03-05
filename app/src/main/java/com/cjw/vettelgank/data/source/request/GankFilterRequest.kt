package com.cjw.vettelgank.data.source.request

import com.cjw.vettelgank.data.GankFilterResult
import com.google.gson.Gson
import java.net.URL

class GankFilterRequest(
    private val filter: String,
    private val page: Int,
    private val count: Int,
    private val gson: Gson = Gson()
) : Request<GankFilterResult?> {

    override fun request(): GankFilterResult? {
        val url = ServerConfig.gankDataFilterApi(filter, page, count)
        val jsonStr = URL(url).readText()
        try {
            return gson.fromJson(jsonStr, GankFilterResult::class.java)
        } catch (e: Exception) {

        }
        return null
    }

}