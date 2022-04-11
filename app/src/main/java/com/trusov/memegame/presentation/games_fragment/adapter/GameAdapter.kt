package com.trusov.memegame.presentation.games_fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.trusov.memegame.databinding.RvItemGameBinding
import com.trusov.memegame.domain.entity.Game

class GameAdapter : ListAdapter<Game, GameViewHolder>(GamesDiffCallback()) {

    var onGameClickListener: ((Game) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = RvItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = currentList[position]
        Log.d("GameAdapter", game.title)
        holder.binding.tvGameTitle.text = game.title
        holder.binding.root.setOnClickListener {
            onGameClickListener?.invoke(game)
        }
    }

}