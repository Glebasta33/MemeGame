package com.trusov.memegame.di

import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.memegame.data.RepositoryImpl
import com.trusov.memegame.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {
        @Provides
        fun provideFirebase(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }
    }
}