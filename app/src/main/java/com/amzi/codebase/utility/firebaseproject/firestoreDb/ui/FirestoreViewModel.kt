package com.amzi.codebase.utility.firebaseproject.firestoreDb.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amzi.codebase.utility.ResultState
import com.amzi.codebase.utility.firebaseproject.firestoreDb.FirestoreModelResponse
import com.amzi.codebase.utility.firebaseproject.firestoreDb.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _res: MutableState<FirestoreState> = mutableStateOf(FirestoreState())
    val res: MutableState<FirestoreState> = _res

    private val _updateRes: MutableState<FirestoreModelResponse> = mutableStateOf(FirestoreModelResponse(
        item = FirestoreModelResponse.FirestoreItem(
            title = "",
            description = ""
        ), key = ""
    ))
    val updateRes: MutableState<FirestoreModelResponse> = _updateRes

    fun setData(data:FirestoreModelResponse){
        _updateRes.value = data
    }

    init {
        getItems()
    }

    fun insert(item: FirestoreModelResponse.FirestoreItem) = repository.insert(item)

    fun getItems() = viewModelScope.launch{
        repository.getItems().collect{
            when(it){
                is ResultState.Failure -> {
                    _res.value = FirestoreState(error = it.message.toString())
                }
                ResultState.Loading -> {
                    _res.value = FirestoreState(isLoading = true)
                }
                is ResultState.Success -> {
                    _res.value = FirestoreState(data = it.data)
                }
            }
        }
    }

    fun delete(key:String) = repository.delete(key)

    fun update(item: FirestoreModelResponse) = repository.update(item)


}


data class FirestoreState(
    val data:List<FirestoreModelResponse> = emptyList(),
    val error:String? = null,
    val isLoading:Boolean? = false
)