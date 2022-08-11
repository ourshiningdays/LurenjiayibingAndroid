package com.bytedance.sdk.share.demo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig
import com.qxy.example.conf.Config


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
        context = applicationContext
        DouYinOpenApiFactory.init(DouYinOpenConfig(Config.clientKey))
    }
}
