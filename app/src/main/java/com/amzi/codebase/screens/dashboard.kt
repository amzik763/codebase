package com.amzi.codebase.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amzi.codebase.R
import com.amzi.codebase.ui.theme.CompactTypography
import com.amzi.codebase.ui.theme.lightBlack
import com.amzi.codebase.ui.theme.lightGrey
import com.amzi.codebase.ui.theme.lighterGrey
import com.amzi.codebase.ui.theme.paleWhite

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
    var query by remember { mutableStateOf("") }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically){
        Image(modifier = Modifier.size(36.dp),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.logoimg),
            contentDescription = "Logo Icon")
        SearchBar(
            query = query,
            onQueryChanged = { query = it },
            onClearQuery = { query = "" }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp)
            .fillMaxWidth()
            .height(40.dp)
            .clip(CircleShape)
            .border(1.5.dp, lighterGrey, CircleShape)
            .background(paleWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search), // Replace with your search icon
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            // Replacing TextField with BasicTextField for more control over padding and background
            BasicTextField(
                value = query,
                onValueChange = onQueryChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                singleLine = true,
                textStyle = CompactTypography.bodyMedium.copy(
                    color = lightBlack,
                    letterSpacing = TextUnit(1f, TextUnitType.Sp),
                    // Using your color, but you can also define this in CompactTypography if needed
                ),
                cursorBrush = SolidColor(lightGrey),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Search here..",
                            style = CompactTypography.bodyMedium.copy(
                                color = lightGrey,
                                letterSpacing = TextUnit(1f, TextUnitType.Sp),
                            )
                        )
                    }
                    innerTextField()
                }
            )

            if (query.isNotEmpty()) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close), // Replace with your clear icon
                        contentDescription = "Clear Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    var searchQuery by remember { mutableStateOf("") }

    SearchBar(
        query = searchQuery,
        onQueryChanged = { newQuery -> searchQuery = newQuery },
        onClearQuery = { searchQuery = "" },
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun SearchBarSample() {
    var query by remember { mutableStateOf("") }

    SearchBar(
        query = query,
        onQueryChanged = { query = it },
        onClearQuery = { query = "" }
    )
}