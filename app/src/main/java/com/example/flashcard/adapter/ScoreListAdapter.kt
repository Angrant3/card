package com.example.flashcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcard.data.PlayerScore
import com.example.flashcard.databinding.ItemScoreBinding

class ScoreListAdapter(private val playerScoreList: List<PlayerScore>) :
    RecyclerView.Adapter<ScoreListAdapter.ScoreViewHolder>() {

    inner class ScoreViewHolder(val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = ItemScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val playerScore = playerScoreList[position]
        holder.binding.tvStt.text = playerScore.stt.toString()
        holder.binding.tvName.text = playerScore.name
        holder.binding.tvScore.text = playerScore.score.toString()
    }

    override fun getItemCount(): Int = playerScoreList.size
}
