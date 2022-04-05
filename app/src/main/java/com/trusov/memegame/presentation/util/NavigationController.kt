package com.trusov.memegame.presentation.util

import com.trusov.memegame.domain.entity.Meme

interface NavigationController {
    fun launchMemeFullScreenFragment(meme: Meme)
}