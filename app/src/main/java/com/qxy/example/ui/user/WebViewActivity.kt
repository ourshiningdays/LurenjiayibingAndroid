package com.qxy.example.ui.user

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.qxy.example.R

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val webView: WebView = findViewById(R.id.webView)
//        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String
            ): Boolean {
                if(URLUtil.isNetworkUrl(url)) {

                    return false
                }
                return try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(view?.url))
                    view?.context?.startActivity(intent)
                    true
                } catch (e: Exception) {
                    true
                }
            }
        }
        webView.clearCache(true);
        webView.reload();
        intent.getStringExtra("url")?.let { webView.loadUrl(it) }
    }



}