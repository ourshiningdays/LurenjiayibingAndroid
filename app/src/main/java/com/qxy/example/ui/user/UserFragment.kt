package com.qxy.example.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.qxy.example.CustomApplication
import com.qxy.example.LoginActivity
import com.qxy.example.R
import com.qxy.example.douyinapi.DouYinAuthUtil

class UserFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_user, container, false)
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

        val followTempButton : Button = view.findViewById(R.id.tempFollowButton)
        followTempButton.setOnClickListener {
            val intent = Intent(activity, FollowActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}