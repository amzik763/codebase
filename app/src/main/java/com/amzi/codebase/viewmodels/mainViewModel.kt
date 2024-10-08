package com.amzi.codebase.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amzi.codebase.dataClasses.ItemType
import com.amzi.codebase.dataClasses.create2DList
import com.amzi.codebase.dataClasses.createCodeSnippets
import com.amzi.codebase.dataClasses.createItemInnerDetails
import com.amzi.codebase.dataClasses.itemMainData
import com.amzi.codebase.repository.myRepository
import com.amzi.codebase.retrofit.response.loginResponse
import com.amzi.codebase.retrofit.response.registerResponse
import com.tillagewireless.ss.data.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class mainViewModel @Inject constructor(private val repository: myRepository) : ViewModel() {

    val selectedItem = MutableStateFlow<Int?>(0)
    val selectedItemType = MutableStateFlow<ItemType>(ItemType.LAYOUT)

    var selectedInnerItem = MutableStateFlow<itemMainData?>(null)

    val allItems = create2DList()
    val allInnerItems = createItemInnerDetails()
    val codeItems = createCodeSnippets()

    private val _layoutItems = MutableStateFlow<List<itemMainData>>(allItems.get(0))
    val layoutItems: StateFlow<List<itemMainData>> = _layoutItems.asStateFlow()

    private val _inputItems = MutableStateFlow<List<itemMainData>>(allItems.get(1))
    val inputItems: StateFlow<List<itemMainData>> = _inputItems.asStateFlow()

    private val _statesItems = MutableStateFlow<List<itemMainData>>(allItems.get(2))
    val statesItems: StateFlow<List<itemMainData>> = _statesItems.asStateFlow()

    private val _loginResponse = MutableStateFlow<Resource<loginResponse>>(Resource.NoAction)
    val loginResponse: StateFlow<Resource<loginResponse>> = _loginResponse

    private val _registerResponse = MutableStateFlow<Resource<registerResponse>>(Resource.NoAction)
    val registerResponse: StateFlow<Resource<registerResponse>> = _registerResponse

    /*init {
        makeList(_layoutItems, _inputItems, _statesItems)
    }*/

    /*private fun makeList(
        _layoutItems: MutableStateFlow<List<itemMainData>>,
        _inputItems: MutableStateFlow<List<itemMainData>>,
        _statesItems: MutableStateFlow<List<itemMainData>>
    ) {

        _layoutItems.value = getItemsByType(ItemType.LAYOUT)
        _inputItems.value = getItemsByType(ItemType.INPUTS)
        _statesItems.value = getItemsByType(ItemType.STATES)

    }*/

    fun updateCurrentPage(currentPage: Int, itemType: String) {
        selectedItem.value = currentPage
        selectedItemType.value = ItemType.entries.firstOrNull { it.value == itemType } ?: ItemType.LAYOUT
    }

    fun getItems(type: ItemType): List<itemMainData> {
        return  when (type) {
            ItemType.LAYOUT -> _layoutItems.value
            ItemType.INPUTS -> _inputItems.value
            ItemType.STATES -> _statesItems.value
            ItemType.UI -> TODO()
            ItemType.MEDIA -> TODO()
            ItemType.DISPLAY -> TODO()
            ItemType.MENU -> TODO()
            ItemType.OTHER -> TODO()
            ItemType.ANIMATION -> TODO()
            ItemType.NAVIGATION -> TODO()
            ItemType.DRAWING -> TODO()
            ItemType.GESTURES -> TODO()
            ItemType.PERMISSIONS -> TODO()
            ItemType.NETWORKING -> TODO()
            ItemType.THEMING -> TODO()
            ItemType.PERFORMANCE -> TODO()
            ItemType.TESTING -> TODO()
            ItemType.ACCESSIBILITY -> TODO()
            ItemType.DATABASE -> TODO()
            ItemType.BACKGROUND_TASKS -> TODO()
            ItemType.SECURITY -> TODO()
            ItemType.CACHING -> TODO()
            ItemType.CLOUD -> TODO()
            ItemType.LOCALIZATION -> TODO()
            ItemType.ERROR_HANDLING -> TODO()
            ItemType.INTEGRATIONS -> TODO()
            ItemType.FILES -> TODO()
            ItemType.NOTIFICATIONS -> TODO()
            ItemType.SENSORS -> TODO()
            ItemType.PAYMENTS -> TODO()
            ItemType.CUSTOM_VIEWS -> TODO()
            ItemType.WEB -> TODO()
            ItemType.DEPENDENCY_INJECTION -> TODO()
            ItemType.MULTI_WINDOW -> TODO()
            ItemType.SCHEDULED_TASKS -> TODO()
        }
    }

    fun login(mobileNumber: String, password: String) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.runAPI(mobileNumber, password)
    }

    fun showString() {
        Log.d("FromViewModel", "Showing String")
        repository.showAnotherMessage()
    }

    fun updateSelectedInnerItem(index: Int?, innerIndex: Int) {
        selectedInnerItem.value = allItems.get(index?:0).get(innerIndex)
    }
}
