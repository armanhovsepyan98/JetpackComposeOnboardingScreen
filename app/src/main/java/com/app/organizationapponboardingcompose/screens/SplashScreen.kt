package com.app.organizationapponboardingcompose.screens

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.app.organizationapponboardingcompose.R
import com.app.organizationapponboardingcompose.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    val alpha = remember {
        Animatable(0f)
    }
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash_icon))
    val context: Context = LocalContext.current

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            1f,
            animationSpec = tween(2000)
        )
        delay(2000)

        if (checkState(context = context)) {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        } else {
            navController.popBackStack()
            navController.navigate(Screen.OnboardingScreen.route)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
    }
}

fun checkState(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("appState", false)
}
