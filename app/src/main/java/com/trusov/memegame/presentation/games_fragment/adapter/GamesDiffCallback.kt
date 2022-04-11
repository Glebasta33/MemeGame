package com.trusov.memegame.presentation.games_fragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.trusov.memegame.domain.entity.Game
import com.trusov.memegame.domain.entity.Meme

class GamesDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}