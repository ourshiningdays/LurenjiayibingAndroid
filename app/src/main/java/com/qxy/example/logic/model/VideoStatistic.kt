package com.qxy.example.logic.model

import com.google.gson.annotations.SerializedName

data class VideoStatistic (
                           @SerializedName("comment_count") val commentCount: Int?,
                           @SerializedName("play_count") val playCount: Int?
                           )