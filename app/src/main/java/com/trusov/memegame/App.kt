package com.trusov.memegame

import android.app.Application
import com.trusov.memegame.di.DaggerApplicationComponent

class App : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}