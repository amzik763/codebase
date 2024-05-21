package com.amzi.codebase.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amzi.codebase.repository.myRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class myViewModel @Inject constructor(private val repository: myRepository): ViewModel() {

    fun showString(){
        Log.d("FromViewModel","Showing String")
        repository.showAnotherMessage()
    }

    fun runAPI(){
        viewModelScope.launch {
            repository.runAPI()
        }
    }

}