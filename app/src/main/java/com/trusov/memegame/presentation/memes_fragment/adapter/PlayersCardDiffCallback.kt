package com.trusov.memegame.presentation.memes_fragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.trusov.memegame.domain.entity.Player

class PlayersCardDiffCallback : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}