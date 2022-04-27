package com.trusov.memegame.presentation.games_fragment.view_model

import androidx.lifecycle.*
import com.trusov.memegame.domain.entity.Game
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.use_case.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GamesHubViewModel @Inject constructor(
    private val getListOfGamesUseCase: GetListOfGamesUseCase,
    private val registerToGameUseCase: RegisterToGameUseCase
) : ViewModel() {

    private val _auth = MutableLiveData<Boolean>()
    val auth: LiveData<Boolean> = _auth

    fun getListOfGames() = getListOfGamesUseCase()

    fun registerToGame(playerName: String, password: String) {
        viewModelScope.launch {
            _auth.value = registerToGameUseCase(playerName, password)!!
        }
    }

}