package com.trusov.memegame.domain.entity

data class Game(
    val title: String,
    val players: List<Player>?,
    var round: Int,
    var id: String = "id"
) {
    fun checkWinner(): Player? {
        val winner = players?.find{it.score >= 10}
        if (winner != null) {
            return winner
        }
        return null
    }

    fun nextRound(){
        round++
    }
}