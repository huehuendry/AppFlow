package com.example.appflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.example.appflow.model.BottomNavItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

/**
 * MainActivity is the entry point of the application. It sets up the navigation and displays
 * the main screen.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppFlowNavigation()
        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem("main_screen", "Home"),
    BottomNavItem("about_screen", "About"),
    BottomNavItem("details_screen", "Details")
)

@Composable
fun AppFlowNavigation() {
    // Create a NavController to manage the navigation
    val navController = rememberNavController()

    // Scaffold to hold the BottomNavigation bar
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main_screen",
            Modifier.padding(innerPadding)
        ) {
            // Main screen composable
            composable("main_screen") {
                AppFlowMainScreen(navController)
            }
            // About screen composable
            composable("about_screen") {
                AboutScreen()
            }
            // Details screen composable
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

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val currentRoute = currentRoute(navController)

        bottomNavItems.forEach { navItem ->
            NavigationBarItem(
                label = { Text(navItem.label) },
                selected = currentRoute == navItem.route,
                onClick = {
                    // Navigate to the selected screen
                    if (navItem.route == "details_screen") {
                        // Navigate to details_screen with a default counter value (e.g., 0)
                        navController.navigate("details_screen/0") {
                            popUpTo("main_screen") { inclusive = false }
                        }
                    } else {
                        navController.navigate(navItem.route) {
                            popUpTo("main_screen") { inclusive = false }
                        }
                    }
                },
                icon = {} // Optional: Add icons here if needed
            )
        }
    }
}


@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun AppFlowMainScreen(navController: NavController) {
    var counter by remember { mutableStateOf(0) }

    // Surface with a background color
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Welcome message
            Text(text = "Welcome to AppFlow!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            // Button to increment the counter
            Button(onClick = { counter++ }) {
                Text("Click Me")
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Display the current value of the counter
            Text("Button clicked $counter times", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(24.dp))
            // Button to navigate to the details screen with the current counter value
            Button(onClick = {
                navController.navigate("details_screen/$counter")
            }) {
                Text("Go to Details")
            }
        }
    }
}

@Composable
fun DetailsScreen(counter: Int) {
    // Surface with a background color
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Details screen title
            Text(text = "Details Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            // Display the counter value passed from the main screen
            Text("You clicked the button $counter times!", fontSize = 18.sp)
        }
    }
}

@Composable
fun AboutScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "About Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text("This is the About Screen!", fontSize = 18.sp, textAlign = TextAlign.Center)
        }
    }
}





/**
 * Preview function for the main screen and details screen.
 * It allows you to preview the UI components in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun AppFlowNavigationPreview() {
    AppFlowNavigation()
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(counter = 5)
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}
