package com.trusov.memegame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.trusov.memegame.App
import com.trusov.memegame.R
import com.trusov.memegame.domain.use_case.GetMemesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getMemesUseCase: GetMemesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.IO).launch {
            val memes = getMemesUseCase.invoke()
            for (meme in memes) {
                Log.d("MainActivity", meme.imageUrl)
            }
        }
    }
}