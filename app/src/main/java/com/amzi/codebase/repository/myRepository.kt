package com.amzi.codebase.repository

import android.util.Log
import javax.inject.Inject

class myRepository @Inject constructor(){

    fun showAnotherMessage(){
        Log.d("Message","Another Message")
    }
}