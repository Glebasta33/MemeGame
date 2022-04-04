package com.trusov.memegame.domain.repository

import com.trusov.memegame.domain.entity.Meme

interface Repository {
    suspend fun getMemes(): List<Meme>
    suspend fun getRandomMeme(): Meme
    suspend fun getRandomQuestion(): String
}