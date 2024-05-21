package com.amzi.codebase.modules

import com.amzi.codebase.repository.myRepository
import com.amzi.codebase.viewmodels.myViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
    @InstallIn(SingletonComponent::class)
    object networkModules {

        @Provides
        @Singleton
        fun provideMyRepository(): myRepository {
            return myRepository()
        }


    }

