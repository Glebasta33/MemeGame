package com.trusov.memegame.presentation.memes_fragment.view_model

import androidx.lifecycle.*
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.use_case.GetMemesUseCase
import com.trusov.memegame.domain.use_case.GetRandomMemeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MemesViewModel @Inject constructor(
    private val getMemesUseCase: GetMemesUseCase,
    private val getRandomMemeUseCase: GetRandomMemeUseCase
) : ViewModel() {

    private var _memes = MutableLiveData<List<Meme>>()
    var memes: LiveData<List<Meme>> = _memes

    fun getMemes() {
        CoroutineScope(Dispatchers.IO).launch {
            _memes.postValue(getMemesUseCase())
        }
    }

    fun getNewMeme(oldMeme: Meme) {
        CoroutineScope(Dispatchers.IO).launch {
            getRandomMemeUseCase(oldMeme)
        }
        getMemes()
    }
}