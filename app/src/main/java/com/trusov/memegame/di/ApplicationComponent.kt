package com.trusov.memegame.di

import android.app.Application
import com.trusov.memegame.presentation.MainActivity
import com.trusov.memegame.presentation.memes_fragment.fragment.MemesFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(instance: MainActivity)
    fun inject(instance: MemesFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}