package com.qxy.example.util

import android.text.TextUtils
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

object Utility {
    fun handleAccessTokenResponse(response: String?): String? {
        if(response == null) return null
        try {
            Log.e("UtilityInAccessToken", response)
            val jsonObject = JSONObject(response)
            val accessToken = jsonObject.getJSONObject("data").getString("access_token")
            Log.e("UtilityInAccessToken", accessToken)
            return accessToken
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return null
    }

    fun handleClientTokenResponse(response: String?): String? {
        if(response == null) return null
        try {
            Log.e("UtilityInClientToken", response)
            val jsonObject = JSONObject(response)
            val clientToken = jsonObject.getJSONObject("data").getString("client_token")
            Log.e("UtilityInClientToken", clientToken)
            return clientToken
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return null
    }
}