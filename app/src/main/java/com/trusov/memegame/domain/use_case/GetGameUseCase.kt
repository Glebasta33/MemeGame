package com.trusov.memegame.domain.use_case

import com.trusov.memegame.domain.repository.Repository
import javax.inject.Inject

class GetGameUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(password: String) = repository.getGame(password)
}