package com.qxy.example.ui.rank

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qxy.example.CustomApplication.Companion.context
import com.qxy.example.R
import com.qxy.example.logic.model.RankList

class RankAdapter(private val fragment: Fragment, private val rankList: List<RankList>) :
    RecyclerView.Adapter<RankAdapter.ViewHolder>(){
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val actorsText : TextView = view.findViewById(R.id.actorsText)
            val areasText : TextView = view.findViewById(R.id.areasText)
            val directorsText : TextView = view.findViewById(R.id.directorsText)
            val discussionHotText : TextView = view.findViewById(R.id.discussionHotText)
//            val idText : TextView = view.findViewById(R.id.idText)

            val influenceHotText : TextView = view.findViewById(R.id.influenceHotText)
//            val maoyanText : TextView = view.findViewById(R.id.maoyanText)
            val nameText : TextView = view.findViewById(R.id.nameText)
            val englishNameText : TextView = view.findViewById(R.id.englishNameText)
            val releaseDateText : TextView = view.findViewById(R.id.releaseDateText)
            val tagsText : TextView = view.findViewById(R.id.tagsText)
            val topicHotText : TextView = view.findViewById(R.id.topicHotText)
//            val typeText : TextView = view.findViewById(R.id.typeText)
            val poster : ImageView = view.findViewById(R.id.posterImg)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rank_item,
            parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rank = rankList[position]
        if(rank.actors != null){
            holder.actorsText.text = context.getString(R.string.actors,
                TextUtils.join("、",rank.actors))
        }

        if(rank.areas != null) {
            holder.areasText.text = context.getString(R.string.areas,
                TextUtils.join("、",rank.areas))
        }

        if(rank.directors != null){
            holder.directorsText.text = context.getString(R.string.directors,
                TextUtils.join("、", rank.directors) )
        }


        holder.discussionHotText.text = context.getString(R.string.discussionHot, rank.discussionHot)

//        holder.idText.text = context.getString(R.string.id, rank.id)

        holder.influenceHotText.text = context.getString(R.string.influenceHot, rank.influenceHot)

//        holder.maoyanText.text = context.getString(R.string.maoyan, rank.maoyan)

        holder.nameText.text = context.getString(R.string.name, rank.name)

        holder.englishNameText.text = context.getString(R.string.englishName, rank.englishName)

        holder.releaseDateText.text = context.getString(R.string.releaseDate, rank.releaseDate)

        if(rank.tags != null){
            holder.tagsText.text = context.getString(R.string.tags, TextUtils.join("、",rank.tags))
        }

        holder.topicHotText.text = context.getString(R.string.topicHot, rank.topicHot)

//        holder.typeText.text = context.getString(R.string.type, rank.type)

        Glide.with(context).load(rank.poster).into(holder.poster)
    }

    override fun getItemCount() = rankList.size


}