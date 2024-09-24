package com.amzi.codebase.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amzi.codebase.dataClasses.Level
import com.amzi.codebase.dataClasses.itemMainData
import com.amzi.codebase.ui.theme.CompactTypography
import com.amzi.codebase.ui.theme.green
import com.amzi.codebase.ui.theme.grey
import com.amzi.codebase.ui.theme.red
import com.amzi.codebase.ui.theme.yellow
import com.amzi.codebase.viewmodels.mainViewModel

@Composable
fun DashboardContent(
    tabTitles: String,
    selectedItemIndex: Int?,
    mainViewModel: mainViewModel,
    items: List<itemMainData>
) {
    Log.d("MainActivity.TAG","DashboardContent" + selectedItemIndex + tabTitles)
    MainItem(tabTitles,mainViewModel,items)
}


@Composable
fun MainItem(tabTitles: String, mainViewModel: mainViewModel, items: List<itemMainData>) {

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
        Text(tabTitles, style = TextStyle(fontFamily = CompactTypography.bodyMedium.fontFamily, color = grey, fontSize = 15.sp))



        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(items) { index, item ->

                Row(modifier = Modifier.fillMaxWidth().height(50.dp)) {
                    LevelCircle(item.itemLevel!!)
                    Column() {
                        Text(item.itemDisplayName.toString())
                        Text("Body")
                    }
                    Stats()
                }

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
