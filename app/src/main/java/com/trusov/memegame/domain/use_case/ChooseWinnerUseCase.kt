package com.trusov.memegame.domain.use_case

import com.trusov.memegame.domain.repository.Repository
import javax.inject.Inject

class ChooseWinnerUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: String) = repository.chooseWinner(id)
}