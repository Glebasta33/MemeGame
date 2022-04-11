package com.trusov.memegame.di

import androidx.lifecycle.ViewModel
import com.trusov.memegame.presentation.games_fragment.view_model.GamesHubViewModel
import com.trusov.memegame.presentation.meme_fullscreen_fragment.view_model.MemeFullScreenViewModel
import com.trusov.memegame.presentation.memes_fragment.view_model.MemesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MemesViewModel::class)
    @Binds
    fun bindMemesViewModel(viewModel: MemesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MemeFullScreenViewModel::class)
    @Binds
    fun bindMemeFullScreenViewModel(viewModel: MemeFullScreenViewModel): ViewModel

    @IntoMap
    @ViewModelKey(GamesHubViewModel::class)
    @Binds
    fun bindGamesHubViewModel(viewModel: GamesHubViewModel): ViewModel


}