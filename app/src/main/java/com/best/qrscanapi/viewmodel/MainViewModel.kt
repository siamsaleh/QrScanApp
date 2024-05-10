package com.best.qrscanapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.best.qrscanapi.model.retrofit.ScanModel
import com.best.qrscanapi.restapi.ApiHelper
import com.schooling.learning.xml.helper.ApiFetchListener

class MainViewModel : ViewModel() {
    private val scanListLiveData = MutableLiveData<List<ScanModel>>()
    private val statusMessageLiveData = MutableLiveData<String>()

    fun getScanList() : MutableLiveData<List<ScanModel>> {
        return scanListLiveData
    }

    fun getStatusMessage(): LiveData<String>{
        return statusMessageLiveData
    }

    fun addScanData(){}

    fun loadScanList(){
        ApiHelper.getScannedData(object : ApiFetchListener<List<ScanModel>>{
            override fun onSuccess(responseData: List<ScanModel>) {
                /*statusMessageLiveData.postValue(responseData.toString())*/ //TODO
            }
            override fun onError(errorMessage: String, responseCode: Int) {
                statusMessageLiveData.postValue("Error: $errorMessage, Code: $responseCode")
            }
        })
    }
    
}