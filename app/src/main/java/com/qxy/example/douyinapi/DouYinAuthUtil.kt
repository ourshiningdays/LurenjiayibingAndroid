package com.qxy.example.douyinapi

import android.app.Activity
import android.app.Application
import android.content.Context
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.qxy.example.conf.Config
import com.qxy.example.logic.network.HttpUtil
import com.qxy.example.logic.network.Utility.handleAccessTokenResponse
import com.qxy.example.logic.network.Utility.handleClientTokenResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Response
import java.io.IOException

object DouYinAuthUtil {
    fun init(activity: Activity){
        val douYinOpenApi: DouYinOpenApi = DouYinOpenApiFactory.create(activity)
        val request = Authorization.Request()
        /*
        request.scope:
        添加试用白名单权限(trial.whitelist)、用户授权时必选权限(user_info)
         */
        request.scope = "trial.whitelist,user_info" //
        //request.callerLocalEntry = "com.xxx.xxx...activity";
        //request.state = "ww"                               // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        douYinOpenApi.authorize(request)
    }
    fun getAccessToken(activity: Activity, authCode: String) {
        var accessToken: String?
        val getAccessTokenURL= "https://open.douyin.com/oauth/access_token/"
        val formBody = FormBody.Builder()
            .add("client_secret", Config.clientSecret)
            .add("code", authCode)
            .add("grant_type", "authorization_code")
            .add("client_key", Config.clientKey)
            .build()

        HttpUtil.sendOkHttpPostRequest(getAccessTokenURL, formBody, callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                accessToken = handleAccessTokenResponse(response.body?.string())
//                println("AuthUtil:" + accessToken)
                val editor = activity.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                editor.putString("access_token", accessToken)
                editor.apply()
            }
        })
    }

    fun getClientToken(activity: Activity) {
        var clientToken: String?
        val getClientTokenURL= "https://open.douyin.com/oauth/client_token/"
        val formBody = FormBody.Builder()
            .add("client_secret", Config.clientSecret)
            .add("grant_type", "client_credential")
            .add("client_key", Config.clientKey)
            .build()

        HttpUtil.sendOkHttpPostRequest(getClientTokenURL, formBody, callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                clientToken = handleClientTokenResponse(response.body?.string())
                println("AuthUtil:" + clientToken)
                val editor = activity.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                editor.putString("client_token", clientToken)
                editor.apply()
            }
        })
    }

    fun getClientToken(application: Application) {
        var clientToken: String?
        val getClientTokenURL= "https://open.douyin.com/oauth/client_token/"
        val formBody = FormBody.Builder()
            .add("client_secret", Config.clientSecret)
            .add("grant_type", "client_credential")
            .add("client_key", Config.clientKey)
            .build()

        HttpUtil.sendOkHttpPostRequest(getClientTokenURL, formBody, callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                clientToken = handleClientTokenResponse(response.body?.string())
                println("AuthUtil:" + clientToken)
                val editor = application.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                editor.putString("client_token", clientToken)
                editor.apply()
            }
        })
    }
}