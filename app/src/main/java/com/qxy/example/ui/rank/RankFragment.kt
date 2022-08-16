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
import com.google.android.material.snackbar.Snackbar
import com.qxy.example.R
import com.qxy.example.databinding.ActivityMainBinding
import com.qxy.example.databinding.FragmentRankBinding
import com.qxy.example.logic.model.RankList
import com.qxy.example.logic.network.Utility
import okhttp3.*
import java.io.IOException


class RankFragment : Fragment() {

    private var isFABOpen = false //底部悬浮按钮是否打开
    private lateinit var fabChange : FloatingActionButton //切换按钮
    private lateinit var fabShow : FloatingActionButton //综艺按钮
    private lateinit var fabMovie : FloatingActionButton //电影按钮
    private lateinit var fabTvSeries : FloatingActionButton //电视剧按钮
    private var _binding: FragmentRankBinding? = null // TODO 暂未binding

    //枚举榜单种类
    enum class RankType(val rankType: String){
        MOVIE("1"),TV_SERIES("2"),SHOW("3")
    }

    //当前榜单，默认为电影
    private var currentRankType = RankType.MOVIE.rankType

    //TODO 暂未binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val rankList = ArrayList<RankList>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View{

        val view = inflater.inflate(R.layout.fragment_rank, container, false)

        fabChange = view.findViewById(R.id.fab_rank_change)
        fabShow = view.findViewById(R.id.fab_rank_show)
        fabTvSeries = view.findViewById(R.id.fab_rank_series)
        fabMovie = view.findViewById(R.id.fab_rank_movie)

        Snackbar.make(view, "点击右下角图标切换榜单", Snackbar.LENGTH_SHORT)
            .setAction("确认") {}.show()

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
            Snackbar.make(view, "数据切换为抖音综艺榜", Snackbar.LENGTH_SHORT)
                .setAction("确认") {}.show()
        }

        fabTvSeries.setOnClickListener {
            currentRankType = RankType.TV_SERIES.rankType
            requestRank()
            Snackbar.make(view, "数据切换为抖音剧集榜", Snackbar.LENGTH_SHORT)
                .setAction("确认") {}.show()
        }

        fabMovie.setOnClickListener {
            currentRankType = RankType.MOVIE.rankType
            Snackbar.make(view, "数据切换为抖音电影榜", Snackbar.LENGTH_SHORT)
                .setAction("确认") {}.show()
            requestRank()
        }
        requestRank()
        return view
    }

    private fun showFABMenu() {
        isFABOpen = true
        //动画效果
        fabShow.animate().translationY(-resources.getDimension(R.dimen.standard_75))
        fabTvSeries.animate().translationY(-resources.getDimension(R.dimen.standard_145))
        fabMovie.animate().translationY(-resources.getDimension(R.dimen.standard_215))
    }

    private fun closeFABMenu() {
        isFABOpen = false
        //恢复动画原始位置
        fabShow.animate().translationY(0F)
        fabTvSeries.animate().translationY(0F)
        fabMovie.animate().translationY(0F)
    }

    /**
     * 请求排行榜信息
     */
    private fun requestRank(){

        // 避免超过限额 本处暂时使用无效网络地址
        val rankUrl = "https://open.douyin.<TEMP>.com/discovery/ent/rank/item?type=$currentRankType"
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
                activity?.runOnUiThread {
                    Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val TAG = "OnResp in RankFragment"
                val rankListResp = Utility.handleGetRankResponse(response.body?.string()!!)
                Log.e(TAG, rankListResp.toString())
                activity?.runOnUiThread {
                    if (rankListResp != null) {
                        showRankInfo(rankListResp)
                        Toast.makeText(activity, "获取榜单信息成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "获取榜单信息失败，请尝试重启App"
                            , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        OkHttpClient().newCall(request).enqueue(callback)
    }

    /**
     * 显示排行榜信息
     */
    private fun showRankInfo(rankList: List<RankList>){
        println("showRankInfo:$rankList")
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView : RecyclerView = requireActivity().findViewById(R.id.rank_recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = RankAdapter(this, rankList)
        recyclerView.adapter = adapter
    }
}