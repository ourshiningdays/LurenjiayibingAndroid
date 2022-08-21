package com.qxy.example.ui.user

import android.R.attr.password
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.qxy.example.CustomApplication
import com.qxy.example.LoginActivity
import com.qxy.example.R
import com.qxy.example.logic.model.UserInfo
import com.qxy.example.logic.network.Utility
import okhttp3.*
import java.io.IOException


class UserFragment : Fragment() {
    private lateinit var nicknameView: TextView
    private lateinit var avatarView: ImageView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        nicknameView = view.findViewById(R.id.user_Nickname)
        avatarView = view.findViewById(R.id.user_Avatar)
        val prefs = CustomApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("access_token", null) //尝试获取现存accessToken
        val openId = prefs.getString("open_id", null)
        val expiredTime = prefs.getLong("expired_time",0)
        if(accessToken == null || openId == null || expiredTime <= System.currentTimeMillis()){
            // 为空或过期要求授权
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        } else{
            Toast.makeText(activity,"当前无需授权",Toast.LENGTH_SHORT).show()
            Log.e("获取到access_token", accessToken)
            Log.e("获取到open_id", openId)
        }
        requestUserData()
        val followButton : Button = view.findViewById(R.id.followButton)
        followButton.setOnClickListener {
            val intent = Intent(activity, FollowActivity::class.java)
            startActivity(intent)
        }

        return view
    }


    private fun requestUserData(){
        val prefs = CustomApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("access_token", "")
        val openId = prefs.getString("open_id", "")
        val url = "https://open.douyin.com/oauth/userinfo/"
//        val jsonObject = JSONObject()
//        try {
//            jsonObject.put("access_token", accessToken)
//            jsonObject.put("open_id", openId)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        val mediaType = "application/json; charset=utf-8".toMediaType()
//        val body = jsonObject.toString().toRequestBody(mediaType)
        val body: RequestBody = FormBody.Builder()
            .add("access_token", accessToken!!)
            .add("open_id", openId!!)
            .build()
        val request: Request = Request.Builder().url(url).post(body).build()

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val userInfo = response.body?.let { Utility.handleUserInfoResponse(it.string()) }
                activity?.runOnUiThread {
                    showUserInfo(userInfo)
                }
            }
        }
        OkHttpClient().newCall(request).enqueue(callback)
    }

    private fun showUserInfo(userInfo: UserInfo?) {
        if (userInfo != null) {
            nicknameView.text = userInfo.nickname
        }
        if (userInfo != null) {
            Glide.with(CustomApplication.context).load(userInfo.avatar)
                .apply(RequestOptions.bitmapTransform(CircleCrop())) //圆形效果
                .into(avatarView)
        }


    }

}