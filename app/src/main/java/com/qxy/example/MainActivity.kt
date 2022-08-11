package com.qxy.example

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qxy.example.databinding.ActivityMainBinding
import com.qxy.example.douyinapi.DouYinAuthUtil


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        //判断是否已经获取到accessToken
        /* 本版本暂时不需要授权
        if(prefs.getString("access_token", null) == null){
            //TODO 判断accessToken是否过期
            Toast.makeText(this, "检测到未获取授权，请获取授权", Toast.LENGTH_SHORT).show()
            DouYinAuthUtil.init(this)
        }
        else{
            Toast.makeText(this, "目前无需授权", Toast.LENGTH_SHORT).show()
        }
        */
        //因为ClientToken只有两个小时的有效期，所以暂时每次打开App时都获取一次
        DouYinAuthUtil.getClientToken(this)


        //以下代码为Android Studio项目模板内置底部Tab代码
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}