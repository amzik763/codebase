package com.amzi.codebase.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amzi.codebase.R
import com.amzi.codebase.ui.theme.paleWhite
import com.amzi.codebase.utility.AnimatedDp
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController) {

    val context = LocalContext.current

    val textValues = listOf("Loading.", "Loading..","Loading...")

    // Use produceState to manage the text change every second
    val currentText by produceState(initialValue = textValues[0]) {
        var index = 0
        while (true) {
            delay(500L) // 1-second delay
            index = (index + 1) % textValues.size // Cycle through the texts
            value = textValues[index] // Update the state with the next text
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = paleWhite),
        contentAlignment = Alignment.Center
    ){
        Logo(context)
        Text(modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp), text = currentText)
    }
}

@Composable
fun Logo(context: Context){
    val animatedSize = AnimatedDp(initialValue = 100.dp, finalValue = 200.dp, duration = 350)
    Image(modifier = Modifier.size(animatedSize), contentScale = ContentScale.Inside, painter =  painterResource(id = R.drawable.logo), contentDescription = "Logo")
}

