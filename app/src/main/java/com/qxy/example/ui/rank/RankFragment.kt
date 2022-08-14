package com.qxy.example.ui.rank

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.sdk.share.demo.CustomApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qxy.example.R
import com.qxy.example.databinding.ActivityMainBinding
import com.qxy.example.databinding.FragmentRankBinding
import com.qxy.example.logic.model.RankList
import com.qxy.example.logic.network.Utility
import okhttp3.*
import java.io.IOException


class RankFragment : Fragment() {
    private var isFABOpen = false
    private lateinit var fabChange : FloatingActionButton
    private lateinit var fabShow : FloatingActionButton
    private lateinit var fabMovie : FloatingActionButton
    private lateinit var fabTvSeries : FloatingActionButton
    private var _binding: FragmentRankBinding? = null
    enum class RankType(val rankType: String){
        MOVIE("1"),TV_SERIES("2"),SHOW("3")
    }
    private var currentRankType = RankType.MOVIE.rankType

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
        fabChange = view.findViewById(R.id.fab_rank_change)
        fabShow = view.findViewById(R.id.fab_rank_show)
        fabTvSeries = view.findViewById(R.id.fab_rank_series)
        fabMovie = view.findViewById(R.id.fab_rank_movie)

        fabChange.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }

        fabShow.setOnClickListener {
            currentRankType = RankType.SHOW.rankType
            requestRank()
        }

        fabTvSeries.setOnClickListener {
            currentRankType = RankType.TV_SERIES.rankType
            requestRank()
        }

        fabMovie.setOnClickListener {
            currentRankType = RankType.MOVIE.rankType
            requestRank()
        }

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
    private fun showFABMenu() {
        isFABOpen = true
        fabShow.animate().translationY(-resources.getDimension(R.dimen.standard_75))
        fabTvSeries.animate().translationY(-resources.getDimension(R.dimen.standard_145))
        fabMovie.animate().translationY(-resources.getDimension(R.dimen.standard_215))
    }

    private fun closeFABMenu() {
        isFABOpen = false
        fabShow.animate().translationY(0F)
        fabTvSeries.animate().translationY(0F)
        fabMovie.animate().translationY(0F)
    }
    private fun requestRank(rankType: String = currentRankType){
        val rankUrl = "https://open.douyin.com/discovery/ent/rank/item?type=$currentRankType"

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