package com.schooling.learning.xml.helper

interface ApiFetchListener<T> {
    fun onSuccess(responseData: T)
    fun onError(errorMessage: String, responseCode: Int)
}