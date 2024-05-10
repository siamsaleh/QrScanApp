package com.best.qrscanapi.viewmodel

import android.content.Context
import android.widget.Toast
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

    fun addScanData(scanData: ScanModel, context: Context) {
        ApiHelper.addScanData(scanData, object : ApiFetchListener<String>{
            override fun onSuccess(responseData: String) {
                Toast.makeText(context, responseData, Toast.LENGTH_SHORT).show()
            }

            override fun onError(errorMessage: String, responseCode: Int) {
                statusMessageLiveData.postValue(errorMessage)
            }
        })
    }

    fun loadScanList(){
        ApiHelper.getScannedData(object : ApiFetchListener<List<ScanModel>>{
            override fun onSuccess(responseData: List<ScanModel>) {
                scanListLiveData.postValue(responseData)
                /*statusMessageLiveData.postValue(responseData.toString())*/ //TODO
            }
            override fun onError(errorMessage: String, responseCode: Int) {
                statusMessageLiveData.postValue("Error: $errorMessage, Code: $responseCode")
            }
        })
    }
    
}