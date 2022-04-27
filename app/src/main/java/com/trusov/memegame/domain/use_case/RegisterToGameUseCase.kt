package com.trusov.memegame.domain.use_case

import com.trusov.memegame.domain.repository.Repository
import javax.inject.Inject

class RegisterToGameUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(playerName: String, password: String): Boolean =
        repository.registerToGame(playerName, password)
}