package com.trusov.memegame.presentation.games_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trusov.memegame.R
import com.trusov.memegame.databinding.RvItemPlayerBinding
import com.trusov.memegame.domain.entity.Player

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    val players = mutableListOf<Player>()

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RvItemPlayerBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        with(holder.binding) {
            tvName.text = player.name.first().toString()
            tvScore.text = player.score.toString()
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }
}