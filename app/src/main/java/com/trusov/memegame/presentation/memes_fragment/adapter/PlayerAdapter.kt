package com.trusov.memegame.presentation.memes_fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.trusov.memegame.R
import com.trusov.memegame.databinding.RvItemPlayerBinding
import com.trusov.memegame.domain.entity.Player
import javax.inject.Inject

class PlayerAdapter @Inject constructor(
    private val auth: FirebaseAuth
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    val players = mutableListOf<Player>()

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RvItemPlayerBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layout = when(viewType){
            OWNER_LAYOUT -> R.layout.rv_item_player_device_owner
            NOT_OWNER_LAYOUT -> R.layout.rv_item_player
            else -> throw RuntimeException("Unknown viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        with(holder.binding) {
            tvName.text = player.name.first().toString()
            tvScore.text = player.score.toString()
            Log.d("PlayerAdapter", "player.isHost: ${player.host}")
            tvQuestionMark.isGone = player.host != 1
            ivCard.isGone = player.chosenMemeUrl == null
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (players[position].id == auth.currentUser?.uid) {
            OWNER_LAYOUT
        } else {
            NOT_OWNER_LAYOUT
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }

    companion object {
        private const val OWNER_LAYOUT = 200
        private const val NOT_OWNER_LAYOUT = 400
    }
}