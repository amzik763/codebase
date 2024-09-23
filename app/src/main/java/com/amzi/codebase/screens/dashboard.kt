package com.amzi.codebase.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amzi.codebase.R
import com.amzi.codebase.dataClasses.ItemType
import com.amzi.codebase.ui.theme.CompactTypography
import com.amzi.codebase.ui.theme.lightBlack
import com.amzi.codebase.ui.theme.lightGrey
import com.amzi.codebase.ui.theme.lighterGrey
import com.amzi.codebase.ui.theme.orangePrimary
import com.amzi.codebase.ui.theme.paleWhite
import com.amzi.codebase.viewmodels.mainViewModel
import kotlinx.coroutines.launch

@Composable
fun Dashboard(navController: NavHostController, mainViewModel: mainViewModel) {
    val selectedItemIndex = mainViewModel.selectedItem.collectAsState()
    Column {
        Log.d("MainActivity.TAG","Dashboard")
        Header()
        ViewPagerWithTabs(selectedItemIndex.value, mainViewModel)

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerWithTabs(selectedItemIndex: Int?, mainViewModel: mainViewModel) {
    val tabTitles = listOf(
        "Layout", "Inputs", "State", "UI", "Media", "Display", "Menu", "Other", "Animation", "Navigation"
    )

    var tasbTitles  = ItemType.entries.associate { it.value to it.info }.toList()

    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) {
        tasbTitles.size
    }

    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState() // Use LazyListState for LazyRow scroll

    // Track tab widths
    val tabWidths = remember { mutableStateMapOf<Int, Int>() }

    // LaunchedEffect to scroll to the active tab when page changes
    LaunchedEffect(pagerState.currentPage) {
        val tabWidth = tabWidths[pagerState.currentPage] ?: return@LaunchedEffect

//        mainViewModel.selectedItem.value = pagerState.currentPage
        mainViewModel.updateCurrentPage(pagerState.currentPage)
        // Scroll to the item in LazyRow, adjusting to prevent cutting off on the left
        coroutineScope.launch {
            lazyListState.animateScrollToItem(
                pagerState.currentPage,
                scrollOffset = 0 // Ensures the selected tab is fully visible from the start
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // LazyRow for the scrollable tab row
        LazyRow(
            state = lazyListState, // Use LazyListState for scrolling
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 0.dp)
        ) {
            itemsIndexed(tasbTitles) { index, (key,value) ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp) // Reduced padding to prevent cutting
                        .onGloballyPositioned { coordinates ->
                            // Capture the width of each tab
                            tabWidths[index] = coordinates.size.width
                        }
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                ) {
                    Column(modifier = Modifier.padding(top = 8.dp),horizontalAlignment = Alignment.Start) {
                        // Text for each tab
                        Text(
                            text = key,
                            color = if (pagerState.currentPage == index) lightBlack else lightGrey,
                            style = TextStyle(
                                fontFamily = CompactTypography.bodyMedium.fontFamily,
                        )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Animate the indicator
                        if (pagerState.currentPage == index) {
                            Box(
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(5.dp)
                                    .padding(start = 2.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(color = orangePrimary)
                            )
                        }
                    }
                }
            }
        }

        // HorizontalPager from the official Jetpack Compose ViewPager library
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            // Content for each page
            DashboardContent(tasbTitles,selectedItemIndex)
        }
    }
}
