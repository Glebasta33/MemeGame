package com.trusov.memegame.presentation.memes_fragment.view_model

import android.util.Log
import androidx.lifecycle.*
import com.trusov.memegame.domain.entity.Game
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.use_case.*
import kotlinx.coroutines.*
import javax.inject.Inject

class MemesViewModel @Inject constructor(
    private val getMemesUseCase: GetMemesUseCase,
    private val getRandomMemeUseCase: GetRandomMemeUseCase,
    private val getRandomQuestionUseCase: GetRandomQuestionUseCase,
    private val getGameUseCase: GetGameUseCase,
    private val getPlayersUseCase: GetPlayersUseCase,
    private val nextRoundUseCase: NextRoundUseCase
) : ViewModel() {

    private var _memes = MutableLiveData<List<Meme>>()
    var memes: LiveData<List<Meme>> = _memes

    private var _question = MutableLiveData<String>()
    var question: LiveData<String> = _question

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MemesViewModel", "$throwable.message")
    }
    private val scope = CoroutineScope(Dispatchers.IO + exceptionHandler)
    fun getMemes() {
        scope.launch {
            _memes.postValue(getMemesUseCase()!!)
        }
    }

    fun getNewMeme(oldMeme: Meme) {
        scope.launch {
            getRandomMemeUseCase(oldMeme)
        }
        getMemes()
    }
    fun getRandomQuestion() {
        scope.launch {
            _question.postValue(getRandomQuestionUseCase()!!)
        }
    }

    fun getGame(password: String) = getGameUseCase(password)
    fun getPlayers(gameId: String) = getPlayersUseCase(gameId)
    fun nextRound() {
        scope.launch {
            nextRoundUseCase()
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}

