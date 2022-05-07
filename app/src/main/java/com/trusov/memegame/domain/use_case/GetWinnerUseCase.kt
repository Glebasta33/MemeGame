package com.trusov.memegame.domain.use_case

import androidx.lifecycle.LiveData
import com.trusov.memegame.domain.repository.Repository
import javax.inject.Inject

class GetWinnerUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(gameId: String) = repository.getWinner(gameId)
}