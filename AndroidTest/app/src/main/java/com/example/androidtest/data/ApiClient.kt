package com.example.androidtest.data

import com.example.androidtest.helper.AppUtils
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val okHttpClient by lazy { OkHttpClient() }

    private val retrofit : Retrofit by lazy {
        val builder = Retrofit.Builder().baseUrl(AppUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val client: OkHttpClient = okHttpClient.newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .build()
        builder.client(client).build()
    }

    fun <T> createService(tClass : Class<T>) : T {
        return retrofit.create(tClass)
    }
}