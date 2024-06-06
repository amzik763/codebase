package com.amzi.codebase.modules

import android.content.Context
import androidx.activity.ComponentActivity
import com.amzi.codebase.utility.FilePickerHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object fileModules {

        @Provides
        @ActivityScoped
        fun provideActivityComponent(@ActivityContext activity: Context) : ComponentActivity{
            return activity as ComponentActivity
        }

    @Provides
    @ActivityScoped
    fun provideOnFilePicked(): (String) -> Unit {
        return { content ->
            // Default implementation, can be overridden if necessary
            println("File content picked: $content")
        }
    }

    @Provides
    @ActivityScoped
    @JvmSuppressWildcards
    fun provideFilePickerHandler(
        activity: ComponentActivity,
        onFilePicked: (String) -> Unit
    ): FilePickerHandler {
        return FilePickerHandler(activity, onFilePicked)
    }
}