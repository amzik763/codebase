package com.amzi.codebase.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.amzi.codebase.repository.myRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class myViewModel @Inject constructor(private val repository: myRepository): ViewModel() {

    fun showString(){
        Log.d("FromViewModel","Showing String")
        repository.showAnotherMessage()
    }


}