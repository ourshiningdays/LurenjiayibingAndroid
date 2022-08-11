package com.qxy.example.ui.rank

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bytedance.sdk.share.demo.CustomApplication
import com.qxy.example.databinding.FragmentRankBinding
import com.qxy.example.logic.model.RankList
import com.qxy.example.logic.network.Utility
import okhttp3.*
import java.io.IOException

class RankFragment : Fragment() {

    private var _binding: FragmentRankBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val rankViewModel =
                ViewModelProvider(this).get(RankViewModel::class.java)

        _binding = FragmentRankBinding.inflate(inflater, container, false)
        val root: View = binding.root
        requestRank()
        val textView: TextView = binding.textRank
        rankViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun requestRank(){
        val rankUrl = "https://open.douyin.com/discovery/ent/rank/item?type=1"
        val prefs = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        val clientToken = prefs.getString("client_token","")
        val request: Request = Request.Builder().addHeader("access-token", clientToken!!)
            .url(rankUrl).get().build()
        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val TAG = "OnResp in RankFragment"
                val rankList = Utility.handleGetRankResponse(response.body?.string()!!)

                Log.e(TAG, rankList.toString())

            }
        }
        OkHttpClient().newCall(request).enqueue(callback)
    }
}