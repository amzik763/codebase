package com.amzi.codebase.utility.firebaseproject.firestoreDb.repository

import com.amzi.codebase.utility.ResultState
import com.amzi.codebase.utility.firebaseproject.firestoreDb.FirestoreModelResponse
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    fun insert(
        item: FirestoreModelResponse.FirestoreItem
    ): Flow<ResultState<String>>

    fun getItems(): Flow<ResultState<List<FirestoreModelResponse>>>

    fun delete(key:String):Flow<ResultState<String>>

    fun update(res: FirestoreModelResponse):Flow<ResultState<String>>
}