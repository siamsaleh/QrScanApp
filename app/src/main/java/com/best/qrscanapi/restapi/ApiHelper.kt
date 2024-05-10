package com.best.qrscanapi.restapi

import com.best.qrscanapi.model.retrofit.ScanModel
import com.schooling.learning.xml.helper.ApiFetchListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHelper {

    companion object{
        fun getScannedData(
            apiFetchListener: ApiFetchListener<List<ScanModel>>
        ){
            val apiService = ApiClient.getInstance().create(ApiService::class.java)

            apiService.getScanCode().enqueue(object : Callback<List<ScanModel>>{
                override fun onResponse(
                    call: Call<List<ScanModel>>,
                    response: Response<List<ScanModel>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { apiFetchListener.onSuccess(it) }
                    } else
                        apiFetchListener.onError(response.message(), response.code())
                }

                override fun onFailure(call: Call<List<ScanModel>>, t: Throwable) {
                    t.message?.let { apiFetchListener.onError(it, 0) }
                }
            })


        }

        fun addScanData(
            scanModel: ScanModel,
            apiFetchListener: ApiFetchListener<String>
        ){
            val apiService = ApiClient.getInstance().create(ApiService::class.java)

            apiService.addScanData(scanModel).enqueue(object : Callback<String>{
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { apiFetchListener.onSuccess(it) }
                    } else
                        apiFetchListener.onError(response.message(), response.code())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    t.message?.let { apiFetchListener.onError(it, 0) }
                }
            })
        }
    }
}