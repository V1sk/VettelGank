package com.cjw.vettelgank.data.source.request

import com.cjw.vettelgank.data.GankDailyResult
import com.google.gson.Gson
import java.net.URL

class GankDailyRequest(private val gson: Gson = Gson()) : Request<GankDailyResult?> {

    override fun request(): GankDailyResult? {
        val url = ServerConfig.gankDailyApi
        val todayJsonStr = URL(url).readText()
        try {
            return gson.fromJson(todayJsonStr, GankDailyResult::class.java)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return null

    }


}