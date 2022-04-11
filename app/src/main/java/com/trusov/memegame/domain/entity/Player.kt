package com.trusov.memegame.domain.entity

data class Player(
        val name: String,
        var score: Int,
        var id: String = "id",
        val memes: List<Meme>?
)
