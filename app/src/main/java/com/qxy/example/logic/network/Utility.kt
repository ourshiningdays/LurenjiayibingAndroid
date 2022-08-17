package com.qxy.example.logic.network

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qxy.example.logic.model.FollowList
import com.qxy.example.logic.model.RankData
import com.qxy.example.logic.model.RankList
import com.qxy.example.logic.model.RankResponse
import org.json.JSONException
import org.json.JSONObject

object Utility {
    fun handleAccessTokenResponse(response: String?): HashMap<String, String>? {
        if(response == null) return null
        try {
            Log.e("UtilityInAccessToken", response)
            val jsonObject = JSONObject(response)
            val accessToken = jsonObject.getJSONObject("data").getString("access_token")
            val openId = jsonObject.getJSONObject("data").getString("open_id")
            Log.e("UtilityInAccessToken", accessToken)
            Log.e("UtilityInAccessToken", openId)
            val hashMap : HashMap<String, String> = HashMap()
            hashMap["AccessToken"] = accessToken
            hashMap["OpenId"] = openId
            return hashMap
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
            //注意下一行代码接口返回字段本身就是access_token，并非client_token打错
            val clientToken = jsonObject.getJSONObject("data").getString("access_token")
            Log.e("UtilityInClientToken", clientToken)
            return clientToken
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return null
    }


    fun handleGetRankResponse(response: String): List<RankList>? {
        try {
            val jsonObject = JSONObject(response)
            Log.e("RankResponse:", response)
//            val data = jsonObject.getJSONObject("data")
            //val jsonArray = data.getJSONArray("list").getJSONObject(0).toString()
            //return Gson().fromJson(jsonArray, RankList::class.java)
            val data = jsonObject.getJSONObject("data")
            val jsonArray = data.getJSONArray("list").toString()
            val type = object: TypeToken<List<RankList>>(){}.type
            return Gson().fromJson(jsonArray, type)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun handleGetFollowResponse(response: String): List<FollowList>? {
        try {
            val jsonObject = JSONObject(response)
            Log.e("FollowResponse:", response)
            val data = jsonObject.getJSONObject("data")
            val jsonArray = data.getJSONArray("list").toString()
            val type = object: TypeToken<List<FollowList>>(){}.type
            return Gson().fromJson(jsonArray, type)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }
}