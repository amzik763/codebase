package com.amzi.codebase.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amzi.codebase.repository.myRepository
import com.amzi.codebase.retrofit.response.loginResponse
import com.amzi.codebase.retrofit.response.registerResponse
import com.tillagewireless.ss.data.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class mainViewModel @Inject constructor(private val repository: myRepository): ViewModel() {


    val selectedItem = MutableStateFlow<Int?>(0)


    private val _loginResponse = MutableStateFlow<Resource<loginResponse>>(Resource.NoAction)
    val loginResponse: StateFlow<Resource<loginResponse>> = _loginResponse

    private val _registerResponse = MutableStateFlow<Resource<registerResponse>>(Resource.NoAction)
    val registerResponse: StateFlow<Resource<registerResponse>> = _registerResponse

    fun showString(){
        Log.d("FromViewModel","Showing String")
        repository.showAnotherMessage()
    }


    fun login(
        mobileNumber: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.runAPI(mobileNumber, password)
    }

    fun updateCurrentPage(currentPage: Int) {
        selectedItem.value = currentPage
    }


}