package com.best.qrscanapi.restapi

import retrofit2.Call
import retrofit2.http.GET
import com.best.qrscanapi.model.retrofit.ScanModel

interface ApiService {

    @GET("scan-codes")
    fun getScanCode(): Call<List<ScanModel>>
}