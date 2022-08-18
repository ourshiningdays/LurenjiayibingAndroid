package com.qxy.example.ui.user

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.qxy.example.CustomApplication
import com.qxy.example.R
import com.qxy.example.logic.model.FollowList


class FollowAdapter (private val fragment: Fragment, private val followList: List<FollowList>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val avatar: ImageView = view.findViewById(R.id.avatarImg)
        val nickname: TextView = view.findViewById(R.id.nickNameText)
        val location: TextView = view.findViewById(R.id.location)
        val gender: TextView = view.findViewById(R.id.gender)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.follow_item,
            parent, false
        )
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follow = followList[position]
        holder.nickname.text = follow.nickname
        when(follow.gender){
            1 -> holder.gender.text = "\u2642\uFE0F" //男
            2 -> holder.gender.text = "\u2640\uFE0F" //女
        }
        val noProvinceList = listOf("北京","上海","天津","重庆")
        var country = follow.country
        var province = follow.province?.replace("省".toRegex(), "")//统一删除"省"
        var city = follow.city?.replace("市".toRegex(), "")//统一删除"市"
        if(country == "") country = null
        if(province == "" || noProvinceList.contains(province)) province = null
        if(city == "") city = null
        if(country == null && province == null && city == null){ //API无地区返回数据则不展示
            holder.location.text = ""
        } else{
            holder.location.text = "\uD83D\uDCCD $country $province ${city}"
                .replace("null".toRegex(),"") //处理API未返回省市情况和遗漏null
        }

        Glide.with(CustomApplication.context).load(follow.avatar)
            .apply(RequestOptions.bitmapTransform(CircleCrop())) //圆形效果
            .into(holder.avatar)
    }
    override fun getItemCount() = followList.size
}