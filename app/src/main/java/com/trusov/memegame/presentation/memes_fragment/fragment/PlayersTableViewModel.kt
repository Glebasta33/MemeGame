package com.trusov.memegame.presentation.memes_fragment.fragment

import androidx.lifecycle.ViewModel
import com.trusov.memegame.domain.use_case.GetPlayersUseCase
import javax.inject.Inject

class PlayersTableViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase
) : ViewModel() {
    // TODO: добавить передачу gameId из MemesFragment в PlayersTableFragment
    fun getPlayers(gameId: String) = getPlayersUseCase(gameId)

}