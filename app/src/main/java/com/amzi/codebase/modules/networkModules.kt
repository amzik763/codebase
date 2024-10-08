package com.amzi.codebase.modules

import android.content.Context
import com.amzi.codebase.retrofit.authAPIs
import com.amzi.codebase.retrofit.remoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
    @InstallIn(SingletonComponent::class)
    object networkModules {

        //no need for this method
        /*@Provides
        @Singleton
        fun provideMyRepository(): myRepository {
            return myRepository()
        }*/

        @Provides
        @Singleton
        fun provideRemoteDataSource(): remoteDataSource {
                return remoteDataSource()
        }

    @Provides
    @Singleton
    fun provideAuthApi(
        remoteDataSource: remoteDataSource,
        @ApplicationContext context: Context
    ): authAPIs {
        return remoteDataSource.buildApi(authAPIs::class.java, context)
    }





    }

