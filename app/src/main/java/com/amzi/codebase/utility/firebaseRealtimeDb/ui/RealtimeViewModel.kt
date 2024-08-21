package com.amzi.codebase.utility.firebaseRealtimeDb.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amzi.codebase.utility.ResultState
import com.amzi.codebase.utility.firebaseRealtimeDb.RealtimeModelResponse
import com.amzi.codebase.utility.firebaseRealtimeDb.RealtimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val repo: RealtimeRepository
) : ViewModel() {

    private val _res: MutableState<ItemState> = mutableStateOf(ItemState())
    val res: State<ItemState> = _res

    private val _updateRes: MutableState<RealtimeModelResponse> = mutableStateOf(
        RealtimeModelResponse(
            item = RealtimeModelResponse.RealtimeItems()
       )
    )
    val updateRes: State<RealtimeModelResponse> = _updateRes


    fun setData(data:RealtimeModelResponse){
        _updateRes.value = data
    }

    fun insert(items: RealtimeModelResponse.RealtimeItems) = repo.insert(items)
    fun delete(key: String) = repo.delete(key)
    fun update(res: RealtimeModelResponse) = repo.update(res)
    init {
        viewModelScope.launch {

            repo.getItems().collect{

                when(it){
                    is ResultState.Success -> {
                        _res.value = ItemState(
                            item = it.data
                        )
                    }
                    ResultState.Loading -> {
                        _res.value = ItemState(
                            isLoading = true
                        )

                    }
                    is ResultState.Failure -> {
                        _res.value = ItemState(
                            error = it.message.toString()
                        )
                    }
                }


            }

        }
    }


}

data class ItemState(

    val item:List<RealtimeModelResponse> = emptyList(),
    val error:String = "",
    val isLoading:Boolean = false

)