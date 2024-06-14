package com.amzi.codebase.modules

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import com.amzi.codebase.repository.myRepository
import com.amzi.codebase.retrofit.authAPIs
import com.amzi.codebase.retrofit.remoteDataSource
import com.amzi.codebase.utility.BASE_URL
import com.amzi.codebase.utility.FilePickerHandler
import com.amzi.codebase.viewmodels.myViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

