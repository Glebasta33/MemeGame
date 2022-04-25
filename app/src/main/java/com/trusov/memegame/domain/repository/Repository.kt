package com.trusov.memegame.domain.repository

import androidx.lifecycle.LiveData
import com.trusov.memegame.domain.entity.Game
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.entity.Player

interface Repository {
    suspend fun getMemes(): List<Meme>
    suspend fun getRandomMeme(oldMeme: Meme)
    suspend fun getRandomQuestion(): String
    fun createNewGame(title: String, password: String)
    fun getListOfGames(): LiveData<List<Game>>
    suspend fun registerToGame(playerName: String, password: String)
}