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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amzi.codebase.R
import com.amzi.codebase.dataClasses.ItemType
import com.amzi.codebase.screens.DashboardContent
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
    val selectedItemIndex by mainViewModel.selectedItem.collectAsState() // Observe the current selected item index.

    Column {
        Log.d("MainActivity.TAG", "Dashboard")
        Header() // Header can remain stateless if it's independent of the mainViewModel.
        ViewPagerWithTabs(selectedItemIndex, mainViewModel, navController) // Pass the selected item to ViewPager.
    }
}

@Composable
fun Header() {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = R.drawable.logoimg),
            contentDescription = "Logo Icon",
            contentScale = ContentScale.Fit
        )

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
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            BasicTextField(
                value = query,
                onValueChange = onQueryChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                singleLine = true,
                textStyle = CompactTypography.bodyMedium.copy(
                    color = lightBlack,
                    letterSpacing = TextUnit(1f, TextUnitType.Sp)
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
                        painter = painterResource(id = R.drawable.ic_close),
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
fun ViewPagerWithTabs(
    selectedItemIndex: Int?,
    mainViewModel: mainViewModel,
    navController: NavHostController
) {

    val titles = remember { ItemType.entries.associate { it.value to it.info }.toList() }

    val pagerState = rememberPagerState(initialPage = selectedItemIndex ?: 0, initialPageOffsetFraction = 0f){
        titles.size
    }

    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    // Track tab widths
    val tabWidths = remember { mutableStateMapOf<Int, Int>() }

    LaunchedEffect(pagerState.currentPage) {
        coroutineScope.launch {
            lazyListState.animateScrollToItem(
                pagerState.currentPage,
                scrollOffset = 0
            )
        }
        mainViewModel.updateCurrentPage(pagerState.currentPage, titles[pagerState.currentPage].first)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            itemsIndexed(titles) { index, (key, _) ->
                TabItem(
                    index = index,
                    title = key,
                    isSelected = pagerState.currentPage == index,
                    onTabClicked = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    },
                    tabWidths = tabWidths
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            DashboardContent(titles[page].second, page, mainViewModel,mainViewModel.allItems.get(page), navController)
        }
    }
}

@Composable
fun TabItem(
    index: Int,
    title: String,
    isSelected: Boolean,
    onTabClicked: () -> Unit,
    tabWidths: MutableMap<Int, Int>
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .onGloballyPositioned { coordinates -> tabWidths[index] = coordinates.size.width }
            .clickable { onTabClicked() }
    ) {
        Column(modifier = Modifier.padding(top = 8.dp), horizontalAlignment = Alignment.Start) {
            Text(
                text = title,
                color = if (isSelected) lightBlack else lightGrey,
                style = TextStyle(fontFamily = CompactTypography.bodyMedium.fontFamily)
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (isSelected) {
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
