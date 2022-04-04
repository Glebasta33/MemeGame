package com.trusov.memegame.di

import com.trusov.memegame.data.RepositoryImpl
import com.trusov.memegame.domain.repository.Repository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository
}