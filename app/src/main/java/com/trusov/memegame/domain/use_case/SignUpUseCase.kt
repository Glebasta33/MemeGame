package com.trusov.memegame.domain.use_case

import com.trusov.memegame.domain.repository.Repository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(name: String, login: String, password: String) =
        repository.singUp(name, login, password)
}