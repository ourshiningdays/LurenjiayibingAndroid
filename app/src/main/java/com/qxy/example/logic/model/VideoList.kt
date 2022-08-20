package com.qxy.example.logic.model

import com.google.gson.annotations.SerializedName

data class VideoList(
    var title: String?,
    @SerializedName("share_url") val shareUrl: String?,
    val statistics: VideoStatistic?,
    val cover: String?,
    @SerializedName("is_top") val isTop: Boolean?,
    @SerializedName("create_time") val createTime: Long?
                    )
