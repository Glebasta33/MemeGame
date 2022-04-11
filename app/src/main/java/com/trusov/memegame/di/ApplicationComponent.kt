package com.trusov.memegame.di

import android.app.Application
import com.trusov.memegame.presentation.MainActivity
import com.trusov.memegame.presentation.games_fragment.fragment.GamesHubFragment
import com.trusov.memegame.presentation.meme_fullscreen_fragment.fragment.MemeFullScreenFragment
import com.trusov.memegame.presentation.memes_fragment.fragment.MemesFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(instance: MainActivity)
    fun inject(instance: MemesFragment)
    fun inject(instance: MemeFullScreenFragment)
    fun inject(instance: GamesHubFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}