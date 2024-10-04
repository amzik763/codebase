package com.amzi.codebase.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amzi.codebase.R
import com.amzi.codebase.dataClasses.itemMainData
import com.amzi.codebase.mainComponent
import com.amzi.codebase.ui.theme.CompactTypography
import com.amzi.codebase.ui.theme.bluePrimary
import com.amzi.codebase.ui.theme.grey
import com.amzi.codebase.ui.theme.lightGrey
import com.amzi.codebase.ui.theme.paleWhite
import com.amzi.codebase.utility.MainScreen
import com.amzi.codebase.viewmodels.mainViewModel

@Composable
fun Items(navController: NavHostController, mainViewModel: mainViewModel) {
    val item = mainViewModel.selectedInnerItem.collectAsState()
    val selectedIndex = remember { mutableStateOf(-1) }
    val nestedItemId = remember { mutableStateOf(-1) }
    val code = remember { mutableStateOf("Click on any tab to view code and UI") }
    if(nestedItemId.value != -1){
        code.value = mainViewModel.codeItems.get(nestedItemId.value).code?:"Happy Learning"
    }else{
        code.value = "Click on any tab to view code and UI"
    }
    Column {
        InnerHeader(item.value?.itemDisplayName)
        Text(text = item.value?.info?:"Happy learning", modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            style = TextStyle(
                fontFamily = CompactTypography.bodyMedium.fontFamily,
                color = grey,
                fontSize = 15.sp,
            ))
        Links(item.value)
        Tabs(item.value?.itemID,mainViewModel,selectedIndex,nestedItemId)
        if(nestedItemId.value != -1)
            Text(modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                text = mainViewModel.allInnerItems.get(nestedItemId.value).info?:"Happy Learning",

                style = TextStyle(
                    fontFamily = CompactTypography.bodyMedium.fontFamily,
                    color = grey,
                    fontSize = 14.sp,
                )
            )
        mainComponent(nestedItemId)
        FullScreenToggle()
        MainScreen(code)
//        ContentInner(item.value,mainViewModel,nestedItemId)
    }
}

@Composable
fun FullScreenToggle() {
    Row(modifier = Modifier.padding(start = 12.dp, top = 16.dp, bottom = 0.dp),verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Code",

            style = TextStyle(
                fontFamily = CompactTypography.bodyMedium.fontFamily,
                color = lightGrey,
                fontSize = 14.sp,
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(modifier = Modifier.padding(top = 2.dp)
            .height(30.dp)
            .wrapContentWidth()
            .clickable {
                //open link ->1
            },
            painter = painterResource(R.drawable.ic_fullscreen),
            contentDescription = "",
            contentScale = ContentScale.Fit)
    }

}

@Composable
fun ContentInner(
    value: itemMainData?,
    mainViewModel: mainViewModel,
    nestedItemId: MutableState<Int>
) {
    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp).fillMaxWidth()
        .background(
            color = paleWhite,
            shape = RoundedCornerShape(8.dp)
        )
        .border( // Add border with conditional width
            width = 1.5.dp,
            color = lightGrey,
            shape = RoundedCornerShape(8.dp)
        ).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(text = mainViewModel.selectedInnerItem.value)
        if(nestedItemId.value != -1){
            Text(text = mainViewModel.codeItems.get(nestedItemId.value).code?:"Happy learning",style = TextStyle(
                fontFamily = CompactTypography.bodyMedium.fontFamily,
                color = grey,
                fontSize = 15.sp,
            ))
        }else{
            Text(text = "Click on any tab to view code and UI",style = TextStyle(
                    fontFamily = CompactTypography.bodyMedium.fontFamily,
                color = grey,
                fontSize = 15.sp,
            ))
        }
    }
}

@Composable
fun Tabs(
    itemID: Int?,
    mainViewModel: mainViewModel,
    selectedIndex: MutableState<Int>,
    nestedItemId: MutableState<Int>
) {
    val filteredList = remember {
        mainViewModel.allInnerItems.filter { it ->
            it.parentID == itemID
        }
    }
    LazyRow(modifier = Modifier.padding(start = 12.dp)) {
        itemsIndexed(filteredList) { index, item ->
            Column(
                modifier = Modifier
                    .padding(end = 0.dp)
                    .wrapContentWidth()
                    .padding(vertical = 0.dp, horizontal = 2.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color =
                        if (index == selectedIndex.value) bluePrimary else paleWhite,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        selectedIndex.value = index
                        nestedItemId.value = item.itemIDInner?:0
                    }
                    .border( // Add border with conditional width
                        width = if (index == selectedIndex.value) 1.5.dp else 1.5.dp,
                        color = if (index == selectedIndex.value) bluePrimary else lightGrey,
                        shape = RoundedCornerShape(16.dp)
                    ),
                verticalArrangement = Arrangement.Center
            ) {
            Text(
                text = item.itemNameInner ?: "Happy learning",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = TextStyle(
                    fontFamily = CompactTypography.bodyMedium.fontFamily,
                    color = if (index == selectedIndex.value) paleWhite else lightGrey,
                    fontSize = 14.sp,
                )
             )
          }
       }
    }
}

@Composable
fun Links(value: itemMainData?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp), verticalAlignment = Alignment.CenterVertically){
        Text(text = "Also available on: ", modifier = Modifier.padding(horizontal = 8.dp),
            style = TextStyle(
                fontFamily = CompactTypography.bodyMedium.fontFamily,
                color = grey,
                fontSize = 14.sp,
            ))
        Spacer(modifier = Modifier.width(12.dp))
        Image(modifier = Modifier
            .height(30.dp)
            .wrapContentWidth()
            .clickable {
                //open link ->1
            },
            painter = painterResource(R.drawable.ic_youtube),
            contentDescription = "",
            contentScale = ContentScale.Fit)
    }
}

@Composable
fun InnerHeader(itemNameInner: String?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(45.dp)
        .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.height(45.dp),
            verticalAlignment = Alignment.CenterVertically) {
                Image(modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = "",
                    contentScale = ContentScale.Fit)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = itemNameInner.toString(),
                style = TextStyle(
                    fontFamily = CompactTypography.titleLarge.fontFamily,
                    color = grey,
                    fontSize = 16.sp,
                )
            )
        }

        Row(modifier = Modifier.height(45.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.premium),
                contentDescription = "",
                contentScale = ContentScale.Fit)
            Spacer(modifier = Modifier.width(12.dp))
            Image(modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.stars),
                contentDescription = "",
                contentScale = ContentScale.Fit)
        }
    }
}