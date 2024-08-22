package com.amzi.codebase.utility.firebaseproject.firestoreDb.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amzi.codebase.utility.ResultState
import com.amzi.codebase.utility.firebaseproject.firebaseRealtimeDb.RealtimeModelResponse
import com.amzi.codebase.utility.firebaseproject.firestoreDb.FirestoreModelResponse
import com.amzi.codebase.utility.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirestoreScreen(isInput: MutableState<Boolean>, viewModel: FirestoreViewModel = hiltViewModel()) {

    val title = remember{ mutableStateOf("") }
    val description = remember{ mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDialog = remember { mutableStateOf(false) }
    val res = viewModel.res.value
    val isUpdate = remember { mutableStateOf(false) }


    if(isUpdate.value){
        onUpdate(isUpdate,viewModel.updateRes.value,isDialog,viewModel)
    }

    if(isInput.value){
        AlertDialog(onDismissRequest = { isInput.value = false },
        ){
            Column(modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(10.dp)) {

                TextField(value = title.value, onValueChange = {
                    title.value = it
                }, placeholder = {
                    Text(text = "title")
                })
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = description.value, onValueChange = {
                    description.value = it
                }, placeholder = {
                    Text(text = "description")
                })
                Spacer(modifier = Modifier.height(10.dp))
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp), onClick = {
                    scope.launch(Dispatchers.Main){
                        viewModel.insert(
                            FirestoreModelResponse.FirestoreItem(
                                title.value,
                                description.value
                            )
                        ).collect{
                            when(it){
                                is ResultState.Failure -> {
                                    context.showToast(it.message.toString())
                                    isDialog.value = false
                                }
                                ResultState.Loading -> {
                                    isDialog.value = true
                                }
                                is ResultState.Success -> {
                                    context.showToast(it.data)
                                    isDialog.value = false
                                    isInput.value = false
                                    viewModel.getItems()
                                }
                            }
                        }
                    }

                }) {

                }
            }
        }
    }

    if(res.data.isNotEmpty()){

        LazyColumn{
            items(
                res.data.size,
                key = {

                    res.data[it].key?:0
                }
            ){

                res.data[it].item?.let { it1 -> EachRow(itemState = it1, onUpdate = {
                    isUpdate.value = true
                    viewModel.setData(res.data[it])
                }, onDelete = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.delete(res.data[it].key?:"").collect{
                                when(it){
                                    is ResultState.Failure -> {
                                        context.showToast(it.message.toString())
                                        isDialog.value = false

                                    }
                                    ResultState.Loading -> {
                                        isDialog.value = true
                                    }
                                    is ResultState.Success -> {
                                        context.showToast(it.data)
                                        isDialog.value = false
                                        viewModel.getItems()
                                    }
                                }
                            }
                        }
                }) }
            }
        }

    }
    if(res.isLoading == true){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
//            CircularProgressIndicator()
        }
    }

    if(res.error?.isNotEmpty() == true){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
            res.error?.let { Text(text = it) }
        }
    }
}


@Composable
fun EachRow(
    itemState: FirestoreModelResponse.FirestoreItem,
    onDelete:()->Unit,
    onUpdate:()->Unit
){
    Card(modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
        ){
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onUpdate()
                }){
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = itemState.title.toString(),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        IconButton(onClick = {
                            onDelete()
                        }, modifier = Modifier.align(CenterVertically)) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }

                        Text(text = itemState.description.toString(),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )

                    }
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun onUpdate(
    isUpdate:MutableState<Boolean>,
    itemState: FirestoreModelResponse,
    isDialog:MutableState<Boolean>,
    viewModel: FirestoreViewModel



){

    val title = remember{ mutableStateOf(itemState.item?.title) }
    val description = remember{ mutableStateOf(itemState.item?.description) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if(isUpdate.value){
        AlertDialog(onDismissRequest = { isUpdate.value = false },
        ){
            Column(modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(10.dp)) {

                TextField(value = title.value?:"", onValueChange = {
                    title.value = it
                }, placeholder = {
                    Text(text = "title")
                })
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = description.value?:"", onValueChange = {
                    description.value = it
                }, placeholder = {
                    Text(text = "description")
                })
                Spacer(modifier = Modifier.height(10.dp))
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp), onClick = {

                    scope.launch(Dispatchers.Main){
                        viewModel.update(
                            FirestoreModelResponse(item = FirestoreModelResponse.FirestoreItem(
                                title.value,
                                description.value
                            ),key = itemState.key?:""
                            )
                        ).collect{
                            when(it){
                                is ResultState.Failure -> {
                                    context.showToast(it.message.toString())
                                }
                                ResultState.Loading -> {
                                }
                                is ResultState.Success -> {
                                    context.showToast(it.data)
                                    isUpdate.value = false
                                    isDialog.value = false
                                    viewModel.getItems()
                                }
                            }
                        }
                    }

                }) {

                }
            }
        }
    }

}

@Composable
fun UpdateData(
    item:FirestoreModelResponse,
    viewModel:FirestoreViewModel,
    isUpdate:MutableState<Boolean>,
    isDialog:MutableState<Boolean>
){

}