package com.qxy.example.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.qxy.example.CustomApplication
import com.qxy.example.R
import com.qxy.example.logic.model.VideoList
import com.qxy.example.logic.network.Utility

import okhttp3.*
import java.io.IOException
class VideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        requestVideo()
        return view
    }
    private fun requestVideo(){
        val prefs = CustomApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("access_token", null)
        val openId = prefs.getString("open_id", null)
        val size = 20
        val followerUrl = "https://open.douyin.com/video/list/?count=$size&open_id=$openId"
        var request: Request? = null
        if(accessToken != null){
            request = Request.Builder().addHeader("access-token", accessToken)
                .url(followerUrl).get().build()
        }


        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val TAG = "OnResp in VideoFrag"
                val videoListResp = response.body?.string()
                    ?.let { Utility.handleGetVideoResponse(it) }
                Log.e(TAG, videoListResp.toString())
                activity?.runOnUiThread {
                    if (videoListResp != null) {
                        showVideoInfo(videoListResp)
//                        Toast.makeText(activity, "获取视频信息成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "获取视频信息失败"
                            , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        if (request != null) {
            OkHttpClient().newCall(request).enqueue(callback)
        }
    }
    /**
     * 显示视频信息
     */
    private fun showVideoInfo(videoList: MutableList<VideoList>){
        println("showVideoInfo:$videoList")
        if(view != null)
        {
            val recyclerView: RecyclerView = requireView().findViewById(R.id.video_recyclerView)
            val layoutManager = StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL
            )

            recyclerView.layoutManager = layoutManager
            val adapter = VideoAdapter(videoList){
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", videoList[it].shareUrl)
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }

    }
}
