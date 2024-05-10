package com.best.qrscanapi.restapi

import retrofit2.Call
import retrofit2.http.GET
import com.best.qrscanapi.model.retrofit.ScanModel
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @GET("scan-codes")
    fun getScanCode(): Call<List<ScanModel>>

    @POST("data")
    fun addScanData(@Body scanModel: ScanModel): Call<String>
}