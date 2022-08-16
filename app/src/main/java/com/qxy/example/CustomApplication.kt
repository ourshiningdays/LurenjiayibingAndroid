package com.qxy.example

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig
import com.qxy.example.conf.Config
import com.qxy.example.douyinapi.DouYinAuthUtil


/**
 * 主要功能：自定义{@link Application}
 */
class CustomApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {

        super.onCreate()
        //因为ClientToken只有两个小时的有效期，所以暂时每次打开App时都获取一次
        DouYinAuthUtil.getClientToken(this)
        context = applicationContext
        DouYinOpenApiFactory.init(DouYinOpenConfig(Config.clientKey))
    }
}
