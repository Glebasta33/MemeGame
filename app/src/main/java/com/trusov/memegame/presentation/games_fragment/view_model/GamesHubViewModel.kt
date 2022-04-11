package com.trusov.memegame.presentation.games_fragment.view_model

import androidx.lifecycle.*
import com.trusov.memegame.domain.entity.Game
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.use_case.GetListOfGamesUseCase
import com.trusov.memegame.domain.use_case.GetMemesUseCase
import com.trusov.memegame.domain.use_case.GetRandomMemeUseCase
import com.trusov.memegame.domain.use_case.GetRandomQuestionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GamesHubViewModel @Inject constructor(
    private val getListOfGamesUseCase: GetListOfGamesUseCase
) : ViewModel() {
    fun getListOfGames() = getListOfGamesUseCase()
}