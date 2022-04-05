package com.trusov.memegame.presentation.memes_fragment.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.trusov.memegame.domain.use_case.GetMemesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MemesViewModel @Inject constructor(
    private val getMemesUseCase: GetMemesUseCase
) : ViewModel() {

    fun getMemes() {
        CoroutineScope(Dispatchers.IO).launch {
            val memes = getMemesUseCase.invoke()
            for (meme in memes) {
                Log.d("MemesViewModel", meme.imageUrl)
            }
        }
    }
}