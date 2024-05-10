package com.best.qrscanapi.model.retrofit

data class ScanModel(
    val qrCode: String,
    val id: String,
    val scanTime: String,
    val type: String
)
