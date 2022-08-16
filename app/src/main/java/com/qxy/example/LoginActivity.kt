package com.qxy.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.qxy.example.douyinapi.DouYinAuthUtil

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Toast.makeText(this, "检测到未获取授权，请获取授权", Toast.LENGTH_SHORT).show()
        DouYinAuthUtil.init(this)

        finish()
    }
}