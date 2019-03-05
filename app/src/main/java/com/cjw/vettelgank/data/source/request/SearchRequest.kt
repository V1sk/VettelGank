package com.cjw.vettelgank.data.source.request

import com.cjw.vettelgank.data.GankFilterResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.net.URL

class SearchRequest(
    private val queryText: String,
    private val page: Int,
    private val count: Int,
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
) : Request<GankFilterResult?> {

    override fun request(): GankFilterResult? {
        val url = ServerConfig.searchApi(queryText, page, count)
        val jsonStr = URL(url).readText()
        try {
            return gson.fromJson(jsonStr, GankFilterResult::class.java)
        } catch (e: Exception) {

        }
        return null
    }

}