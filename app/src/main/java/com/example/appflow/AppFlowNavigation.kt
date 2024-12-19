package com.example.appflow

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.appflow.screens.SplashScreen
import androidx.navigation.compose.*
import androidx.navigation.navArgument

@Composable
fun AppFlowNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash_screen") {
                SplashScreen(navController = navController)
            }
            composable("main_screen") {
                AppFlowMainScreen(navController)
            }
            composable("about_screen") {
                AboutScreen()
            }
            composable(
                route = "details_screen/{counter}",
                arguments = listOf(navArgument("counter") { type = NavType.IntType })
            ) { backStackEntry ->
                val counter = backStackEntry.arguments?.getInt("counter") ?: 0
                DetailsScreen(counter)
            }

        }
    }
}