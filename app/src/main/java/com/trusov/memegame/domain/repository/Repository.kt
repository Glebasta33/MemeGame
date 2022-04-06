package com.trusov.memegame.domain.repository

import androidx.lifecycle.LiveData
import com.trusov.memegame.domain.entity.Meme

interface Repository {
    suspend fun getMemes(): List<Meme>
    suspend fun getRandomMeme(oldMeme: Meme)
    suspend fun getRandomQuestion(): String
}