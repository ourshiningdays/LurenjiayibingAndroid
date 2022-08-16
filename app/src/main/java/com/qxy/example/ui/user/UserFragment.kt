package com.qxy.example.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.qxy.example.CustomApplication
import com.qxy.example.LoginActivity
import com.qxy.example.R

class UserFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        val prefs = CustomApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
        if(prefs.getString("access_token", null) == null){
            //TODO 判断accessToken是否有效
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        else Toast.makeText(activity, "目前无需授权", Toast.LENGTH_SHORT).show()
        prefs.getString("access_token","")?.let { Log.e("获取到access_token", it) }

        return view
    }

}