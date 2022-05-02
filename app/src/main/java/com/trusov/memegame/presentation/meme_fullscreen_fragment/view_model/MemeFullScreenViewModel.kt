package com.trusov.memegame.presentation.meme_fullscreen_fragment.view_model

import androidx.lifecycle.ViewModel
import com.trusov.memegame.domain.use_case.ChooseMemeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MemeFullScreenViewModel @Inject constructor(
    private val chooseMemeUseCase: ChooseMemeUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun chooseMeme(imageUrl: String) {
        scope.launch {
            chooseMemeUseCase(imageUrl)
        }
    }
}