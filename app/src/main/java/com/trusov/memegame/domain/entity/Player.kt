package com.trusov.memegame.domain.entity

data class Player(
        val name: String,
        var score: Int = 0,
        var id: String,
        var currentGameId: String
)
