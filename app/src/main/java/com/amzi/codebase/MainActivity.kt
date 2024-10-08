package com.amzi.codebase

import Dashboard
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amzi.codebase.screens.Items
import com.amzi.codebase.screens.Splash
import com.amzi.codebase.ui.theme.CodebaseTheme
import com.amzi.codebase.utility.FilePickerHandler
import com.amzi.codebase.utility.REQUEST_WRITE_STORAGE
import com.amzi.codebase.utility.firebaseproject.firebaseRealtimeDb.ui.FireBaseRealtimeScreen
import com.amzi.codebase.utility.firebaseproject.firestoreDb.ui.FirestoreScreen
import com.amzi.codebase.utility.navigationRoutes
import com.amzi.codebase.viewmodels.mainViewModel
import com.amzi.codebase.viewmodels.uiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: mainViewModel by viewModels()
    private val uiViewModel: uiViewModel by viewModels()

    //or
//  @Inject
//  lateinit var viewModel2: myViewModel

    @Inject lateinit var filePickerHandler: FilePickerHandler

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, proceed with logging
            } else {
                // Permission denied, handle accordingly
            }
        }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodebaseTheme {
                val isInsert = remember{
                    mutableStateOf(false)
                }
                val isInput = remember {
                    mutableStateOf(false)
                }
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission already granted, proceed with logging
                } else {
                    // You can directly ask for the permission.
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Scaffold (
                   /* floatingActionButton = {
                        val currentRoute = CurrentRoute(navController = navController)

                            FloatingActionButton(onClick = {
                                if(currentRoute == navigationRoutes.firebase.route)
                                    isInsert.value = true
                                else if(currentRoute == navigationRoutes.firestore.route)
                                    isInput.value = true
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "Add")

                            }
                    }*/
                    ){
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        mainViewModel.showString()
//                    Greeting("Android")
                        AppNavigation(navController, isInsert, isInput, mainViewModel)
//                    LoginScreen(viewmodel = viewModel, filePickerHandler)
                    }
                }
            }
        }
        // Request write permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_STORAGE)
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    isInsert: MutableState<Boolean>,
    isInput: MutableState<Boolean>,
    mainViewModel: mainViewModel
) {
    NavigationGraph(navController,isInsert,isInput,mainViewModel)
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isInsert: MutableState<Boolean>,
    isInput: MutableState<Boolean>,
    mainViewModel: mainViewModel
) {
    NavHost(navController = navController, startDestination =navigationRoutes.splash.route) {
        composable(navigationRoutes.splash.route) {
            Splash(navController)
        }
        composable(navigationRoutes.dashboard.route) {
            Dashboard(navController,mainViewModel)
        }
        composable(navigationRoutes.items.route) {
            Items(navController,mainViewModel)
        }
        composable(navigationRoutes.main.route) {
            Screen1(navController)
        }
        composable(navigationRoutes.settings.route) {
            Screen2(onNavigateToScreen1 = { navController.navigate(navigationRoutes.main.route) })
        }
        composable(navigationRoutes.firebase.route) {
            FireBaseRealtimeScreen( isInsert)
        }
        composable(navigationRoutes.firestore.route) {
            FirestoreScreen( isInput)
        }
    }
}

// Screen1.kt
@Composable
fun Screen1(navController: NavHostController) {
    Column {
        Button(
            onClick = { navController.navigate(navigationRoutes.settings.route) },
            modifier = Modifier
                .width(200.dp)
                .height(55.dp)
        ) {
            Text("Go to Screen 2")
        }
        Button(
            onClick = { navController.navigate(navigationRoutes.firebase.route) },
            modifier = Modifier
                .width(200.dp)
                .height(55.dp)
        ) {
            Text("Fire Firebase")
        }
        Button(
            onClick = { navController.navigate(navigationRoutes.firestore.route) },
            modifier = Modifier
                .width(200.dp)
                .height(55.dp)
        ) {
            Text("Fire Firestore")
        }
    }
}

// Screen1.kt
@Composable
fun Screen2(onNavigateToScreen1: () -> Unit) {
    Button(onClick = onNavigateToScreen1) {
        Text("Go to Screen 1")
    }
}

@Composable
fun CurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    return navBackStackEntry?.destination?.route
}

@Composable
fun ChangeStatusBarColor(color: Color, darkIcons: Boolean = false) {
    val activity = LocalContext.current as Activity
    val window = activity.window

    // Change the status bar color
    window.statusBarColor = color.toArgb()

    // For changing the status bar icon color (light/dark)
    val insetsController = WindowCompat.getInsetsController(window, window.decorView)
    insetsController.isAppearanceLightStatusBars = darkIcons
}
