package com.amzi.codebase

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amzi.codebase.ui.theme.lightGrey
import com.amzi.codebase.ui.theme.paleWhite


@Composable
fun mainComponent(id: MutableState<Int>){

    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 12.dp).fillMaxWidth()
        .background(
            color = paleWhite,
            shape = RoundedCornerShape(8.dp)
        )
        .border( // Add border with conditional width
            width = 1.5.dp,
            color = lightGrey,
            shape = RoundedCornerShape(8.dp)
        ).padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        when(id.value){
            -1 -> {
                Nothing()
            }
            1 ->{
                CheckboxSimple()
            }
            2 ->{
                CheckboxCustom()
            }
        }
    }
}


@Composable
fun Nothing(){

    // Remember the checked state
    val checkedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "Generating...")
    }

}


@Composable
fun CheckboxSimple(){

    // Remember the checked state
    val checkedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically
    ){
        // Checkbox with a state
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )
        // Displaying the current state
        Text(text = if (checkedState.value) "Checked" else "Unchecked")
    }

}


@Composable
fun CheckboxCustom(){

    // Remember the checked state
    val checkedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically
    ){
        // Checkbox with a state
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )
        // Displaying the current state
        Text(text = if (checkedState.value) "Got it" else "Not got it")
    }

}