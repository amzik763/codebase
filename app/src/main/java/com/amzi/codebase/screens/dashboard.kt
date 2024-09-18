package com.amzi.codebase.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amzi.codebase.R

@Composable
fun Dashboard(navController: NavHostController) {

    Column {
        Log.d("MainActivity.TAG","Dashboard")
        Header()
    }


}

@Preview(showBackground = true)
@Composable
fun Header(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically){
        Image(modifier = Modifier.size(36.dp),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.logoimg),
            contentDescription = "Logo Icon")
    }

}