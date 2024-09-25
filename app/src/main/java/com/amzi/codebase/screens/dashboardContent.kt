package com.amzi.codebase.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amzi.codebase.R
import com.amzi.codebase.dataClasses.Level
import com.amzi.codebase.dataClasses.itemMainData
import com.amzi.codebase.ui.theme.CompactTypography
import com.amzi.codebase.ui.theme.green
import com.amzi.codebase.ui.theme.grey
import com.amzi.codebase.ui.theme.lightGrey
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
        Text( tabTitles, textAlign = TextAlign.Justify, style = TextStyle(fontFamily = CompactTypography.bodyMedium.fontFamily, color = grey, fontSize = 15.sp))



        LazyColumn(
            modifier = Modifier.padding(top = 16.dp).fillMaxSize()
        ) {
            itemsIndexed(items) { index, item ->

                Row(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth().height(44.dp)) {
                    LevelCircle(item.itemLevel!!)
                    Spacer(modifier = Modifier.size(8.dp))
                    Column(modifier = Modifier.fillMaxWidth(0.55f)) {
                        Text(item.itemDisplayName.toString(), style = TextStyle(fontFamily = CompactTypography.bodyMedium.fontFamily, color = grey, fontSize = 16.sp))
                        Text(TrimmedText(item.info!!), style = TextStyle(fontFamily = CompactTypography.bodyMedium.fontFamily, color = lightGrey, fontSize = 14.sp))
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Stats()
                }
//                Spacer(modifier = Modifier.height(4.dp).fillMaxWidth())

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
    Box(modifier = Modifier.padding(top = 6.dp).size(8.dp)
        .clip(CircleShape)
        .background(color = colour ))

}

@Composable
fun Stats(){
            Row(modifier = Modifier.fillMaxHeight(1f), verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(12.dp))
                Image(painter = painterResource(R.drawable.ic_star), contentDescription = "Eye Icon",  modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("4.7k", style = TextStyle(fontFamily = CompactTypography.labelSmall.fontFamily, color = grey, fontSize = 12.sp))
                Spacer(modifier = Modifier.width(12.dp))
                Image(painter = painterResource(R.drawable.ic_eye), contentDescription = "Eye Icon",  modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("4.7k", style = TextStyle(fontFamily = CompactTypography.labelSmall.fontFamily, color = grey, fontSize = 12.sp))

            }

}


fun TrimmedText(itemInfo: String) : String {
    val trimmedText = if (itemInfo.length > 30) {
        itemInfo.take(25) + "..." // Trim to 30 characters and add ellipses if longer
    } else {
        itemInfo // Display as is if shorter than 30 characters
    }
    return trimmedText
}