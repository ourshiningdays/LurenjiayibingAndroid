package com.qxy.example.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qxy.example.CustomApplication.Companion.context
import com.qxy.example.R
import com.qxy.example.logic.model.VideoList
import java.text.SimpleDateFormat
import java.util.*

class VideoAdapter(private val videoList: MutableList<VideoList>, val itemClick:(Int) -> Unit) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.findViewById(R.id.videoImg)
        val title: TextView = view.findViewById(R.id.videoTitle)
        val uploadTime: TextView = view.findViewById(R.id.videoUploadTime)
        val comment: TextView = view.findViewById(R.id.videoComment)
        val play: TextView = view.findViewById(R.id.videoPlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.video_item,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoAdapter.ViewHolder, position: Int) {
        val video = videoList[position]
        if(video.title == "" || video.title == null)
            video.title = context.getString(R.string.noTitle)
        holder.title.text = video.title
        holder.comment.text = context.getString(R.string.commentCount, video.statistics?.commentCount)
        holder.play.text = context.getString(R.string.playCount,  video.statistics?.playCount)
        holder.uploadTime.text = video.createTime?.let { getDateTime(it) }
        holder.itemView.setOnClickListener { itemClick(position) }
        Glide.with(context).load(video.cover).into(holder.cover)
        if(video.isTop == true){
            val item: VideoList = videoList[position]
            item.title = context.getString(R.string.topEmoji, item.title)
            holder.title.text = item.title
            videoList.remove(item) // remove item from the list
            videoList.add(0, item) // add at 0 index of your list

            //notifyDataSetChanged()
        }
    }

    override fun getItemCount() = videoList.size

    private fun getDateTime(time: Long): String? {
        return try {
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.CHINA)
            val netDate = Date(time * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }


}