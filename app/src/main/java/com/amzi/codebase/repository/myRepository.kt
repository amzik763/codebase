package com.amzi.codebase.repository

import android.util.Log
import com.amzi.codebase.retrofit.authAPIs
import javax.inject.Inject

class myRepository @Inject constructor(
    private val authAPIs: authAPIs
){

    fun showAnotherMessage(){
        Log.d("Message","Another Message")
    }

    suspend fun runAPI(){
        Log.d("Running API","AUTH API STARTED")
        authAPIs.login("9721174847","1234")
    }


}