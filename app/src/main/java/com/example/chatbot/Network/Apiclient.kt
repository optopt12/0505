package com.example.chatbot.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Apiclient {
    /**
     * HTTP 攔截器
     */
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(LogInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Chat GPT API
     */
    val openAI: ApiService by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.openai.com/v1/chat/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    /**
     * Google Places API
     */
    val googlePlaces: ApiService by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}