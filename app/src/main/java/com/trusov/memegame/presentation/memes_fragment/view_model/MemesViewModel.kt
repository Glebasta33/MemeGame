package com.trusov.memegame.presentation.memes_fragment.view_model

import androidx.lifecycle.*
import com.trusov.memegame.domain.entity.Game
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.use_case.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemesViewModel @Inject constructor(
    private val getMemesUseCase: GetMemesUseCase,
    private val getRandomMemeUseCase: GetRandomMemeUseCase,
    private val getRandomQuestionUseCase: GetRandomQuestionUseCase,
    private val getGameUseCase: GetGameUseCase
) : ViewModel() {

    private var _memes = MutableLiveData<List<Meme>>()
    var memes: LiveData<List<Meme>> = _memes

    private var _question = MutableLiveData<String>()
    var question: LiveData<String> = _question

    fun getMemes() {
        CoroutineScope(Dispatchers.IO).launch {
            _memes.postValue(getMemesUseCase()!!)
        }
    }

    fun getNewMeme(oldMeme: Meme) {
        CoroutineScope(Dispatchers.IO).launch {
            getRandomMemeUseCase(oldMeme)
        }
        getMemes()
    }

    fun getRandomQuestion() {
        CoroutineScope(Dispatchers.IO).launch {
            _question.postValue(getRandomQuestionUseCase()!!)
        }
    }

    fun getGame(password: String) = getGameUseCase(password)
}