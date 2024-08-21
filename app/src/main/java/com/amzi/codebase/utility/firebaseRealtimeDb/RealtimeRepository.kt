package com.amzi.codebase.utility.firebaseRealtimeDb

import com.amzi.codebase.utility.ResultState
import kotlinx.coroutines.flow.Flow

interface RealtimeRepository {

    fun insert(
        items: RealtimeModelResponse.RealtimeItems
    ): Flow<ResultState<String>>

    fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>>

    fun delete(
        key:String
    ):Flow<ResultState<String>>

    fun update(
        res: RealtimeModelResponse
    ):Flow<ResultState<String>>
}