package com.bytedance.sdk.share.demo;

import android.app.Application;

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;
import com.qxy.example.conf.Config


/**
 * 主要功能：自定义{@link Application}
 */
class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DouYinOpenApiFactory.init(DouYinOpenConfig(Config.clientKey))
    }
}
