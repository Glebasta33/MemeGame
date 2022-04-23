package com.trusov.memegame.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trusov.memegame.App
import com.trusov.memegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}