package com.trusov.memegame.presentation.memes_fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.trusov.memegame.R
import com.trusov.memegame.databinding.RvItemMemeCardBinding
import com.trusov.memegame.domain.entity.Meme
import kotlinx.coroutines.*

class MemeCardAdapter : ListAdapter<Meme, MemeCardAdapter.MemeCardViewHolder>(MemesDiffCallback()) {

    var onMemeClickListener: ((Meme) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeCardViewHolder {
        val binding = RvItemMemeCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MemeCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemeCardViewHolder, position: Int) {
        val meme = currentList[position]
        Picasso.get().load(meme.imageUrl)
            .placeholder(R.drawable.paper_placeholder)
            .into(holder.binding.ivMeme, object : Callback {
                override fun onSuccess() {
                    Log.d("MemesAdapter", "success")
                }

                override fun onError(e: Exception?) {
                    Log.d("MemesAdapter", e?.message ?: "error")
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(1000L)
                        withContext(Dispatchers.Main) {
                            Picasso.get().load(meme.imageUrl)
                                .placeholder(R.drawable.paper_placeholder)
                                .into(holder.binding.ivMeme)
                        }
                    }
                    Log.d("MemesAdapter", e?.message ?: "error")

                }

            })
        holder.binding.root.setOnClickListener {
            onMemeClickListener?.invoke(meme)
        }
    }

    class MemeCardViewHolder(
        val binding: RvItemMemeCardBinding
    ) : RecyclerView.ViewHolder(binding.root)
}