package com.trusov.memegame.presentation.memes_fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.trusov.memegame.R
import com.trusov.memegame.databinding.MemeRvItemBinding
import com.trusov.memegame.domain.entity.Meme
import java.lang.Exception

class MemesAdapter : ListAdapter<Meme, MemeViewHolder>(MemesDiffCallback()) {

    var onMemeClickListener: ((Meme) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val binding = MemeRvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val meme = currentList[position]
        Log.d("MemesAdapter", meme.imageUrl)
        Picasso.get().load(meme.imageUrl)
            .placeholder(R.drawable.paper_placeholder)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.binding.ivMeme, object : Callback {
                override fun onSuccess() {
                    Log.d("MemesAdapter", "success")
                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }

            })
        holder.binding.root.setOnClickListener {
            onMemeClickListener?.invoke(meme)
        }
    }
}