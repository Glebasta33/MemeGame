package com.trusov.memegame.domain.entity

data class Player(
        val name: String,
        var score: Int = 0,
        var id: String = "id",
        var currentGameId: String = "id",
        var host: Int = 0,
        var winner: Int = 0,
        var chosenMemeUrl: String? = null
)
