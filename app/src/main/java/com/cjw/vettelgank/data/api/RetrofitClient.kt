package com.cjw.vettelgank.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object RetrofitClient {

    private const val BASE_URL = "http://gank.io/api/"

    private const val DATE_PATTERN_1 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    private const val DATE_PATTERN_2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val DATE_PATTERN_3 = "yyyy-MM-dd'T'HH:mm:ss"

    val INSTANCE: GankService = create()

    private fun create(): GankService {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val dateGson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, GankDateDeserializer(DATE_PATTERN_1, DATE_PATTERN_2, DATE_PATTERN_3))
            .serializeNulls()
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(dateGson))
            .build()
            .create(GankService::class.java)
    }

}