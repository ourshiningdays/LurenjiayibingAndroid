package com.qxy.example.ui.rank

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.sdk.share.demo.CustomApplication
import com.qxy.example.R
import com.qxy.example.databinding.ActivityMainBinding.inflate
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
    private val rankList = ArrayList<RankList>()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View{
//    ): View {
//        val rankViewModel = ViewModelProvider(this).get(RankViewModel::class.java)
        //_binding = FragmentRankBinding.inflate(inflater, container, false)
        //val root: View = binding.root
        val view = inflater.inflate(R.layout.fragment_rank, container, false)
        requestRank()

        //val textView: TextView = binding.textRank
//        rankViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestRank(){
        val rankUrl = "https://open.douyin.com/discovery/ent/rank/item?type=1"

//        val prefs = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)

        val prefs = CustomApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
        var clientToken = prefs.getString("client_token","")
        //TODO 下面的解决方案只是临时的。 后续必须修改
        while(clientToken == ""){
           clientToken = prefs.getString("client_token","")
        }
        val request: Request = Request.Builder().addHeader("access-token", clientToken!!)
            .url(rankUrl).get().build()
        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val TAG = "OnResp in RankFragment"
                val rankListResp = Utility.handleGetRankResponse(response.body?.string()!!)
                Log.e(TAG, rankListResp.toString())
                requireActivity().runOnUiThread {
                    if (rankListResp != null) {
                        showRankInfo(rankListResp)
                        Toast.makeText(
                            requireActivity(),
                            "获取排名信息成功", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "获取排名信息失败！", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        OkHttpClient().newCall(request).enqueue(callback)
    }

    private fun showRankInfo(rankList: List<RankList>){
        println("showRankInfo:$rankList")
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView : RecyclerView = requireActivity().findViewById(R.id.rank_recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = RankAdapter(this, rankList)
        recyclerView.adapter = adapter
    }
}