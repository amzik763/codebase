package com.amzi.codebase.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amzi.codebase.R
import com.amzi.codebase.viewmodels.mainViewModel

@Composable
fun items(navController: NavHostController, mainViewModel: mainViewModel, updateData:()->(Unit)) {
    Column {
        innerHeader()
        Text(text = "This is the info text")
    }

}


@Composable
fun innerHeader(){

    Row(modifier = Modifier.fillMaxWidth().height(45.dp).padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.height(45.dp),
            verticalAlignment = Alignment.CenterVertically) {
                Image(modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = "",
                    contentScale = ContentScale.Inside)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Items")


        }
    }
}