package com.trusov.memegame.presentation.memes_fragment.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trusov.memegame.domain.use_case.ChooseWinnerUseCase
import com.trusov.memegame.domain.use_case.GetPlayersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayersTableViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val chooseWinnerUseCase: ChooseWinnerUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun getPlayers(gameId: String) = getPlayersUseCase(gameId)

    fun chooseWinner(playerId: String) {
        scope.launch {
            chooseWinnerUseCase(playerId)
        }
    }
}