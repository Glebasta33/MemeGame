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
    suspend fun registerToGame(playerId: String, password: String): Boolean
    fun getGame(password: String): LiveData<Game?> // TODO: add id as 2nd param
    fun singUp(name: String, login: String, password: String)
    fun getPlayers(gameId: String): LiveData<List<Player>>
    suspend fun nextRound()
    suspend fun chooseMeme(imageUrl: String)
    suspend fun chooseWinner(id: String)
    fun getWinner(gameId: String): LiveData<Player>
}