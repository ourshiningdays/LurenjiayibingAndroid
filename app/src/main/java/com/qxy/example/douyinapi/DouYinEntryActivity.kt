package com.qxy.example.douyinapi
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import com.bytedance.sdk.open.aweme.CommonConstants
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler
import com.bytedance.sdk.open.aweme.common.model.BaseReq
import com.bytedance.sdk.open.aweme.common.model.BaseResp
import com.bytedance.sdk.open.aweme.share.Share
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.qxy.example.MainActivity

/**
 * 主要功能：接受授权返回结果的activity
 * <p>
 * <p>
 * 也可通过request.callerLocalEntry = "com.xxx.xxx...activity"; 定义自己的回调类
 */
class DouYinEntryActivity: Activity(),IApiEventHandler {

    private lateinit var douYinOpenApi : DouYinOpenApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        douYinOpenApi = DouYinOpenApiFactory.create(this)
        douYinOpenApi.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {

    }

    @Override
    override fun onResp(resp: BaseResp) {
        if (resp.type == CommonConstants.ModeType.SHARE_CONTENT_TO_TT_RESP) {
            val response = resp as Share.Response
            Toast.makeText(this, " code：" + response.errorCode + " 文案：" + response.errorMsg, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else if (resp.type == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            val response = resp as Authorization.Response
            if (resp.isSuccess) {
               Toast.makeText(this, "授权成功，获得权限：" + response.grantedPermissions,
                    Toast.LENGTH_LONG).show()
                DouYinAuthUtil.getAccessToken(this, resp.authCode)
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onErrorIntent(intent: Intent?) {
        // 错误数据
        Toast.makeText(this, "Intent出错", Toast.LENGTH_LONG).show()
    }
}
