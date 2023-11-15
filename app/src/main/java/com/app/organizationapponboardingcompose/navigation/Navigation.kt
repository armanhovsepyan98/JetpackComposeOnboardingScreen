package com.app.organizationapponboardingcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.organizationapponboardingcompose.screens.HomeScreen
import com.app.organizationapponboardingcompose.screens.OnboardingScreen
import com.app.organizationapponboardingcompose.screens.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.OnboardingScreen.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object OnboardingScreen : Screen("onboardingScreen")
    object Home : Screen("home")
}
