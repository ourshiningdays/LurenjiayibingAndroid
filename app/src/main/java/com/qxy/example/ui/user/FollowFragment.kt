package com.qxy.example.ui.user

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qxy.example.CustomApplication
import com.qxy.example.R
import com.qxy.example.logic.model.FollowList
import com.qxy.example.logic.network.Utility
import okhttp3.*
import java.io.IOException


class FollowFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_follow, container, false)
        requestFollow()
        return view
    }

    private fun requestFollow(){
        val prefs = CustomApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("access_token", null)
        val openId = prefs.getString("open_id", null)
        val size = 20
        val followUrl = "https://open.douyin.com/following/list/?count=$size&open_id=$openId"
        val request: Request = Request.Builder().addHeader("access-token", accessToken!!)
            .url(followUrl).get().build()

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val TAG = "OnResp in FollowFrag"
                val followListResp = Utility.handleGetFollowResponse(response.body?.string()!!)
                Log.e(TAG, followListResp.toString())
                activity?.runOnUiThread {
                    if (followListResp != null) {
                        showFollowInfo(followListResp)
                        Toast.makeText(activity, "获取关注信息成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "获取关注信息失败"
                            , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        OkHttpClient().newCall(request).enqueue(callback)
    }

    /**
     * 显示关注信息
     */
    private fun showFollowInfo(followList: List<FollowList>){
        println("showFollowInfo:$followList")
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView : RecyclerView = requireActivity().findViewById(R.id.follow_recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = FollowAdapter(followList)
        recyclerView.adapter = adapter
    }
}