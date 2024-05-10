package com.best.qrscanapi.restapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var retrofit: Retrofit? = null

    /*private const val BASE_URL = "http://localhost:3000/"*/
    private const val BASE_URL = "https://qrscanserver.onrender.com/"

    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): Retrofit {
        return retrofit ?: synchronized(this) {
            retrofit ?: retrofitInstance.also { retrofit = it }
        }
    }
}
