package com.trusov.memegame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.trusov.memegame.App
import com.trusov.memegame.R
import com.trusov.memegame.databinding.ActivityMainBinding
import com.trusov.memegame.di.ViewModelFactory
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.domain.use_case.GetMemesUseCase
import com.trusov.memegame.presentation.meme_fullscreen_fragment.fragment.MemeFullScreenFragment
import com.trusov.memegame.presentation.memes_fragment.fragment.MemesFragment
import com.trusov.memegame.presentation.util.NavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationController {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MemesFragment())
            .commit()
    }

    override fun launchMemeFullScreenFragment(meme: Meme) {
        supportFragmentManager.beginTransaction()
            .addToBackStack("MemeFullScreenFragment")
            .replace(R.id.fragment_container, MemeFullScreenFragment.createNewInstance(meme.imageUrl))
            .commit()
    }
}