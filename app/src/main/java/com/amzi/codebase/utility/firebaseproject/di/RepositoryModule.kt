package com.amzi.codebase.utility.firebaseproject.di

import com.amzi.codebase.utility.firebaseproject.firebaseRealtimeDb.RealtimeDbRepository
import com.amzi.codebase.utility.firebaseproject.firebaseRealtimeDb.RealtimeRepository
import com.amzi.codebase.utility.firebaseproject.firestoreDb.repository.FirestoreDbRepositoryImpl
import com.amzi.codebase.utility.firebaseproject.firestoreDb.repository.FirestoreRepository
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

    @Binds
    abstract fun bindFirestoreRepository(
        repo: FirestoreDbRepositoryImpl
    ): FirestoreRepository
}