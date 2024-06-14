package com.amzi.codebase.utility

sealed class navigationRoutes(val route: String) {
    object main : navigationRoutes("Main")
    object settings : navigationRoutes("Settings")
    // ...
}