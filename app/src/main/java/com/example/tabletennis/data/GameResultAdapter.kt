package com.example.tabletennis.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletennis.databinding.ItemLayoutBinding

class GameResultAdapter() :
    RecyclerView.Adapter<GameResultAdapter.MainViewHolder>() {

    private var resultList: List<ScoreDbEntity>? = null

    inner class MainViewHolder(val itemBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val winner = itemBinding.tvResWinner
        val pNameOne = itemBinding.tvResPlayerNameOne
        val pNameTwo = itemBinding.tvResPlayerNameTwo
        val pScoreOne = itemBinding.tvResScoreOne
        val pScoreTwo = itemBinding.tvResScoreTwo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = resultList?.get(position)
        item?.let { result ->
            holder.winner.text = result.winner
            holder.pNameOne.text = result.pNameOne
            holder.pNameTwo.text = result.pNameTwo
            holder.pScoreOne.text = result.pScoreOne
            holder.pScoreTwo.text = result.pScoreTwo
        }
//        holder.itemBinding.tvDateTitle.text = resultList[position].date.toString()
    }

    fun initList(list: List<ScoreDbEntity>) {
        resultList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = resultList?.size ?: 0
}