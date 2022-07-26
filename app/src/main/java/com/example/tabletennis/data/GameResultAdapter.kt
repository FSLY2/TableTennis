package com.example.tabletennis.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletennis.databinding.ItemLayoutBinding
import com.example.tabletennis.models.ScoreDbEntity

class GameResultAdapter(private val resultList: List<ScoreDbEntity>) :
    RecyclerView.Adapter<GameResultAdapter.MainViewHolder>() {


    inner class MainViewHolder(val itemBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
//        holder.itemBinding.tvDateTitle.text = resultList[position].date.toString()
        holder.itemBinding.tvResWinner.text = resultList[position].winner
        holder.itemBinding.tvResPlayerNameOne.text = resultList[position].pNameOne
        holder.itemBinding.tvResPlayerNameTwo.text = resultList[position].pNameTwo
        holder.itemBinding.tvResScoreOne.text = resultList[position].pScoreOne.toString()
        holder.itemBinding.tvResScoreTwo.text = resultList[position].pScoreTwo.toString()
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}