package com.amzi.codebase.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.amzi.codebase.dataClasses.Level
import com.amzi.codebase.ui.theme.green
import com.amzi.codebase.ui.theme.red
import com.amzi.codebase.ui.theme.yellow

@Composable
fun DashboardContent(tasbTitles: List<Pair<String, String>>, selectedItemIndex: Int?) {
    Log.d("MainActivity.TAG","DashboardContent" + selectedItemIndex)
    MainItem()
}


@Composable
fun MainItem(){
    val itemsList = (1..100).toList() // List of 100 items

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(itemsList) { index, item ->

            Row(){
                LevelCircle(Level.EASY)
                Column() {
                    Text("Header")
                    Text("Body")
                }
                Stats()
            }

        }
    }
}

@Composable
fun LevelCircle(level: Level){

    val colour = when(level){
        Level.EASY -> {
            green
        }
        Level.MEDIUM -> {
            yellow
            }
        Level.HARD -> {
            red
        }
    }
    Box(modifier = Modifier.size(8.dp)
        .clip(CircleShape)
        .background(color = colour ))

}

@Composable
fun Stats(){



}
