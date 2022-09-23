package com.example.tabletennis.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletennis.common.PlayerNumber
import com.example.tabletennis.databinding.ItemLayoutBinding
import com.example.tabletennis.models.GameDetails
import java.text.SimpleDateFormat

class GameResultAdapter() :
    RecyclerView.Adapter<GameResultAdapter.MainViewHolder>() {

    private var resultList: MutableList<GameDetails>? = null

    inner class MainViewHolder(binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val winner = binding.tvResWinner
        val pNameOne = binding.tvResPlayerNameOne
        val pNameTwo = binding.tvResPlayerNameTwo
        val date = binding.tvDateTitle
        val scoreOne = binding.tvResScoreOne
        val scoreTwo = binding.tvResScoreTwo
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
        resultList?.get(position)?.let { result ->
            holder.apply {
                pNameOne.text = result.firstPlayer.pName
                pNameTwo.text = result.secondPlayer.pName
                result.date?.let {
                    val dateFormat = SimpleDateFormat("dd MMM, yy 'at' HH:mm")
                    date.text = dateFormat.format(it)
                    // TODO: "Fix bug with time" 
                }
                scoreOne.text = result.firstPlayer.score.toString()
                scoreTwo.text = result.secondPlayer.score.toString()
                result.winner?.let {
                    val name = when (it) {
                        PlayerNumber.FIRST -> result.firstPlayer.pName
                        PlayerNumber.SECOND -> result.secondPlayer.pName
                    }
                    winner.text = name
                }
            }
        }
    }

    fun initList(list: List<GameDetails>) {
        resultList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun deleteItem(gameDetails: GameDetails) {
        val position = resultList?.indexOf(gameDetails)
        if (position != null) {
            notifyItemRemoved(position)
            resultList?.removeAt(position)
        }
    }

    fun setItem(gameDetails: GameDetails) {
        resultList?.add(gameDetails)
        resultList?.sortBy { it.id }
        val position = resultList?.indexOf(gameDetails)
        position?.let { notifyItemInserted(it) }
    }

    fun getItemByPosition(position: Int): GameDetails? {
        return resultList?.get(position)?.copy()
    }

    override fun getItemCount(): Int = resultList?.size ?: 0
}