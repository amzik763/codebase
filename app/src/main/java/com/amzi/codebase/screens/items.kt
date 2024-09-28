package com.amzi.codebase.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amzi.codebase.R
import com.amzi.codebase.dataClasses.itemMainData
import com.amzi.codebase.ui.theme.CompactTypography
import com.amzi.codebase.ui.theme.grey
import com.amzi.codebase.viewmodels.mainViewModel

@Composable
fun Items(navController: NavHostController, mainViewModel: mainViewModel) {
    val item = mainViewModel.selectedInnerItem.collectAsState()
    Column {
        InnerHeader(item.value?.itemDisplayName)
        Text(text = item.value?.info?:"Happy learning", modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            style = TextStyle(
                fontFamily = CompactTypography.bodyMedium.fontFamily,
                color = grey,
                fontSize = 15.sp,
            ))
        Links(item.value)
    }
}

@Composable
fun Links(value: itemMainData?) {

    Row(modifier = Modifier.fillMaxWidth().height(56.dp), verticalAlignment = Alignment.CenterVertically){
        Text(text = "Also available on: ", modifier = Modifier.padding(horizontal = 8.dp),
            style = TextStyle(
                fontFamily = CompactTypography.bodyMedium.fontFamily,
                color = grey,
                fontSize = 14.sp,
            ))
        Spacer(modifier = Modifier.width(12.dp))
        Image(modifier = Modifier.height(30.dp).wrapContentWidth().clickable {
            //open link ->
        },
            painter = painterResource(R.drawable.ic_youtube),
            contentDescription = "",
            contentScale = ContentScale.Fit)
    }
}

@Composable
fun InnerHeader(itemNameInner: String?) {
    Row(modifier = Modifier.fillMaxWidth().height(45.dp).padding(horizontal = 8.dp, vertical = 4.dp),
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