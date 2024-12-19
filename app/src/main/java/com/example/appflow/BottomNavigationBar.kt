package com.example.appflow

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.appflow.model.BottomNavItem
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*

val bottomNavItems = listOf(
    BottomNavItem("main_screen", "Home", Icons.Filled.Home),
    BottomNavItem("about_screen", "About", Icons.Filled.Info),
    BottomNavItem("details_screen", "Details", Icons.AutoMirrored.Filled.List)
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val currentRoute = currentRoute(navController)
        bottomNavItems.forEach { navItem ->
            NavigationBarItem(
                label = { Text(navItem.label) },
                selected = currentRoute == navItem.route,
                onClick = {
                    when (navItem.route) {
                        "details_screen" -> {
                            // For details_screen, we need to pass a counter value
                            navController.navigate("details_screen/0") { // Pass a default counter value (e.g., 0)
                                popUpTo("main_screen") { inclusive = false }
                            }
                        }
                        else -> {
                            // For other screens, we can navigate normally
                            navController.navigate(navItem.route) {
                                popUpTo("main_screen") { inclusive = false }
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label
                    )
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
