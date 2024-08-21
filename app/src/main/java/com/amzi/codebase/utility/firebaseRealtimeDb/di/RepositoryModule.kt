package com.amzi.codebase.utility.firebaseRealtimeDb.di

import com.amzi.codebase.utility.firebaseRealtimeDb.RealtimeDbRepository
import com.amzi.codebase.utility.firebaseRealtimeDb.RealtimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        repo: RealtimeDbRepository
    ): RealtimeRepository
}