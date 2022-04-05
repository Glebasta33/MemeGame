package com.trusov.memegame.presentation.memes_fragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.trusov.memegame.domain.entity.Meme

class MemesDiffCallback : DiffUtil.ItemCallback<Meme>() {
    override fun areItemsTheSame(oldItem: Meme, newItem: Meme): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Meme, newItem: Meme): Boolean {
        return oldItem == newItem
    }
}