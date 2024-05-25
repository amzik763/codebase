package com.amzi.codebase.repository

import android.util.Log
import com.amzi.codebase.retrofit.authAPIs
import com.tillagewireless.ss.data.network.SafeApiCall
import javax.inject.Inject

class myRepository @Inject constructor(
    private val authAPIs: authAPIs
):SafeApiCall{

    fun showAnotherMessage(){
        Log.d("Message","Another Message")
    }

    suspend fun runAPI(mobile:String,password:String)
         = safeApiCall {
            authAPIs.login(mobile,password)
    }



}