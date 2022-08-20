package com.qxy.example.logic.model

import com.google.gson.annotations.SerializedName

data class RankData(@SerializedName("active_Time") val activeTime: String,
                    val description: String,
                    @SerializedName("error_code") val errorCode: String,
                    val rank: List<RankList>)