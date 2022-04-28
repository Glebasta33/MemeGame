package com.trusov.memegame.di

import android.app.Application
import com.trusov.memegame.presentation.MainActivity
import com.trusov.memegame.presentation.games_fragment.fragment.GamesHubFragment
import com.trusov.memegame.presentation.meme_fullscreen_fragment.fragment.MemeFullScreenFragment
import com.trusov.memegame.presentation.memes_fragment.fragment.MemesFragment
import com.trusov.memegame.presentation.new_game_fragment.fragment.CreateNewGameFragment
import com.trusov.memegame.presentation.sign_up_fragment.SignUpFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(instance: MainActivity)
    fun inject(instance: MemesFragment)
    fun inject(instance: MemeFullScreenFragment)
    fun inject(instance: GamesHubFragment)
    fun inject(instance: CreateNewGameFragment)
    fun inject(instance: SignUpFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}