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
import androidx.navigation.NavController
import androidx.navigation.navArgument

/**
 * MainActivity is the entry point of the application. It sets up the navigation and displays
 * the main screen.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Start the navigation with the NavHost
            AppFlowNavigation()
        }
    }
}

/**
 * AppFlowNavigation is the composable function that sets up navigation for the app.
 * It manages the navigation between the main screen and the details screen.
 */
@Composable
fun AppFlowNavigation() {
    // Create a NavController to manage the navigation
    val navController = rememberNavController()

    // Set up the NavHost with different screen routes
    NavHost(navController = navController, startDestination = "main_screen") {
        // Main screen composable
        composable("main_screen") {
            AppFlowMainScreen(navController)
        }
        // Details screen composable with a route argument for the counter value
        composable(
            route = "details_screen/{counter}",
            arguments = listOf(navArgument("counter") { type = NavType.IntType })
        ) { backStackEntry ->
            val counter = backStackEntry.arguments?.getInt("counter") ?: 0
            DetailsScreen(counter)
        }
    }
}

/**
 * AppFlowMainScreen is the main screen of the app.
 * It displays a welcome message, a button to increment a counter, and a button to navigate to the details screen.
 * @param navController The navigation controller used for navigation between screens.
 */
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

/**
 * DetailsScreen displays the details of the app, showing the number of times the button was clicked.
 * @param counter The number of times the button was clicked on the main screen.
 */
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

/**
 * Preview function for the main screen and details screen.
 * It allows you to preview the UI components in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun AppFlowNavigationPreview() {
    AppFlowNavigation()
}
