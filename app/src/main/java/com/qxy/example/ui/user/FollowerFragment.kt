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


class FollowerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_follower, container, false)
        requestFollower()
        return view
    }

    private fun requestFollower(){
        val prefs = CustomApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("access_token", null)
        val openId = prefs.getString("open_id", null)
        val size = 20
        val followerUrl = "https://open.douyin.com/fans/list/?count=$size&open_id=$openId"
        val request: Request = Request.Builder().addHeader("access-token", accessToken!!)
            .url(followerUrl).get().build()

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val TAG = "OnResp in FollowerFrag"
                val followerListResp = Utility.handleGetFollowResponse(response.body?.string()!!)
                Log.e(TAG, followerListResp.toString())
                activity?.runOnUiThread {
                    if (followerListResp != null) {
                        showFollowerInfo(followerListResp)
                        Toast.makeText(activity, "获取粉丝信息成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "获取粉丝信息失败"
                            , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        OkHttpClient().newCall(request).enqueue(callback)
    }

    /**
     * 显示粉丝信息
     */
    private fun showFollowerInfo(followerList: List<FollowList>){
        println("showFollowerInfo:$followerList")
        val layoutManager = LinearLayoutManager(activity)
        if(view != null){
            val recyclerView : RecyclerView = requireView().findViewById(R.id.follower_recyclerView)
            recyclerView.layoutManager = layoutManager
            val adapter = FollowAdapter(followerList)
            recyclerView.adapter = adapter
        }

    }
}