package com.amzi.codebase

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amzi.codebase.screens.auth.LoginScreen
import com.amzi.codebase.ui.theme.CodebaseTheme
import com.amzi.codebase.utility.REQUEST_WRITE_STORAGE
import com.amzi.codebase.viewmodels.myViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amzi.codebase.utility.FilePickerHandler
import com.amzi.codebase.utility.navigationRoutes
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: myViewModel by viewModels()

    //or
//  @Inject
//  lateinit var viewModel2: myViewModel

    @Inject lateinit var filePickerHandler: FilePickerHandler

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permissiongranted, proceed with logging
            } else {
                // Permission denied, handle accordingly
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodebaseTheme {
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

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    viewModel.showString()
//                    Greeting("Android")
                    AppNavigation()
//                    LoginScreen(viewmodel = viewModel, filePickerHandler)
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
fun AppNavigation() {
    val navController = rememberNavController()
    NavigationGraph(navController)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination =navigationRoutes.main.route) {
        composable(navigationRoutes.main.route) {
            Screen1(onNavigateToScreen2 = { navController.navigate(navigationRoutes.settings.route) })
        }
        composable(navigationRoutes.settings.route) {
            Screen2(onNavigateToScreen1 = { navController.navigate(navigationRoutes.main.route) })
        }
    }
}



// Screen1.kt
@Composable
fun Screen1(onNavigateToScreen2: () -> Unit) {
    Button(onClick = onNavigateToScreen2) {
        Text("Go to Screen 2")
    }
}

// Screen1.kt
@Composable
fun Screen2(onNavigateToScreen1: () -> Unit) {
    Button(onClick = onNavigateToScreen1) {
        Text("Go to Screen 1")
    }
}
