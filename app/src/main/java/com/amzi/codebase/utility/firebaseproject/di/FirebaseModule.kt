package com.amzi.codebase.utility.firebaseproject.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideRealtimeDb(): DatabaseReference  = Firebase.database.reference

    @Provides
    @Singleton
    fun provideFirestoreDb(): FirebaseFirestore = Firebase.firestore
}