package com.qxy.example.util

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


object HttpUtil {
    fun sendOkHttpGetRequest(address: String, callback: okhttp3.Callback) {
        val request: Request = Request.Builder().url(address).get().build()
        OkHttpClient().newCall(request).enqueue(callback)
    }
    fun sendOkHttpPostRequest(address: String, formBody: FormBody, callback: okhttp3.Callback) {
        val request: Request = Request.Builder().url(address).post(formBody).build()
        OkHttpClient().newCall(request).enqueue(callback)
    }
}