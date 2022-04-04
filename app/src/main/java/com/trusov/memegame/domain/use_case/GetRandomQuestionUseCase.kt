package com.trusov.memegame.domain.use_case

import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.repository.Repository
import javax.inject.Inject

class GetRandomQuestionUseCase @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(): String {
        return repository.getRandomQuestion()
    }
}