package com.amzi.codebase.screens

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
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
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@Composable
fun Dashboard(navController: NavHostController) {

    Column {
        Log.d("MainActivity.TAG","Dashboard")
        Header()
        ViewPagerWithTabs()

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
fun ViewPagerWithTabs() {
    val tabTitles = listOf(
        "All", "Layout", "Input", "Widgets", "Layout", "Input", "Widgets",
        "Layout", "Input", "Widgets"
    )
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) {
        tabTitles.size
    }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Track tab offsets and widths
    val tabPositions = remember { mutableStateMapOf<Int, IntOffset>() }
    val tabWidths = remember { mutableStateMapOf<Int, Int>() }
    val localDensity = LocalDensity.current
    val localConfiguration = LocalConfiguration.current
    // Use LaunchedEffect to handle page changes and scrolling
    LaunchedEffect(pagerState.currentPage) {
        val page = pagerState.currentPage
        val tabPosition = tabPositions[page] ?: return@LaunchedEffect
        val tabWidth = tabWidths[page] ?: return@LaunchedEffect

        val scrollX = scrollState.value.toFloat()
        val screenWidthPx = with(localDensity) { localConfiguration.screenWidthDp.dp.toPx() }
        val tabLeftEdge = tabPosition.x.toFloat()
        val tabRightEdge = tabLeftEdge + tabWidth

        // Log values for debugging
        Log.d("ViewPagerWithTabs", "Current Page: $page")
        Log.d("ViewPagerWithTabs", "Tab Position: $tabPosition")
        Log.d("ViewPagerWithTabs", "Tab Width: $tabWidth")
        Log.d("ViewPagerWithTabs", "Scroll X: $scrollX")
        Log.d("ViewPagerWithTabs", "Screen Width (px): $screenWidthPx")
        Log.d("ViewPagerWithTabs", "Tab Left Edge: $tabLeftEdge")
        Log.d("ViewPagerWithTabs", "Tab Right Edge: $tabRightEdge")

        // Determine the scroll offset
        val targetScrollX: Int = when {
            tabLeftEdge < scrollX -> {
                Log.d("ViewPagerWithTabs", "Scrolling left to: ${tabLeftEdge.toInt()}")
                tabLeftEdge.toInt() // Scroll left
            }
            tabRightEdge > scrollX - page*40 + screenWidthPx-> {
                val scrollTarget = (tabRightEdge - screenWidthPx).toInt() + page*40
                Log.d("ViewPagerWithTabs", "Scrolling right to: $scrollTarget")
                scrollTarget // Scroll right
            }
            else -> {
                Log.d("ViewPagerWithTabs", "No scrolling needed")
                return@LaunchedEffect // No scrolling needed
            }
        }

        // Animate the scroll to the target position
        scrollState.animateScrollTo(targetScrollX)
        Log.d("ViewPagerWithTabs", "Animated Scroll To: $targetScrollX")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Custom scrollable tab row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(vertical = 8.dp, horizontal = 0.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .onGloballyPositioned { coordinates ->
                            // Capture the global position and width of each tab
                            tabPositions[index] = IntOffset(coordinates.positionInWindow().x.toInt(), 0)
                            tabWidths[index] = coordinates.size.width
                        }
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Text for each tab
                        Text(
                            text = title,
                            color = if (pagerState.currentPage == index) Color.Black else Color.Gray,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Animate the indicator
                        if (pagerState.currentPage == index) {
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(Color(0xFFFFA500))
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
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Page: ${tabTitles[page]}", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
