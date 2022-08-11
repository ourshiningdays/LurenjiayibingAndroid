package com.qxy.example.logic.model

import com.google.gson.annotations.SerializedName

data class RankResponse(val data: RankData)

data class RankData(@SerializedName("active_Time") val activeTime: String,
                val description: String,
                @SerializedName("error_code") val errorCode: String,
                val rank: List<RankList>)

data class RankList(val actors: List<String>, val areas: List<String>,
                    val directors: List<String>,
                    @SerializedName("discussion_hot") val discussionHot: String,
                    val id: String,
                    @SerializedName("influence_hot") val influenceHot: String,
                    @SerializedName("maoyan_id") val maoyan: String,
                    val name: String,
                    @SerializedName("name_en") val englishName: String,
                    val poster: String,
                    @SerializedName("release_date") val releaseDate: String,
                    val tags: List<String>,
                    @SerializedName("topic_hot") val topicHot: String,
                    val type: String)
