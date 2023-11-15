package com.app.organizationapponboardingcompose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.app.organizationapponboardingcompose.ui.theme.TEXT_COLOR

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Home",
            color = TEXT_COLOR,
            fontSize = 50.sp
        )
    }
}