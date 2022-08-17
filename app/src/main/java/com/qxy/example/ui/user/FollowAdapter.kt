package com.qxy.example.ui.user

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qxy.example.CustomApplication
import com.qxy.example.R
import com.qxy.example.logic.model.FollowList


class FollowAdapter (private val fragment: Fragment, private val followList: List<FollowList>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val avatar: ImageView = view.findViewById(R.id.avatarImg)
        val nickname: TextView = view.findViewById(R.id.nickNameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.follow_item,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follow = followList[position]
        holder.nickname.text = follow.nickname
        Glide.with(CustomApplication.context).load(follow.avatar).into(holder.avatar)
    }
    override fun getItemCount() = followList.size
}