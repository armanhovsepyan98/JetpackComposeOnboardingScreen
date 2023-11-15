package com.app.organizationapponboardingcompose.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.app.organizationapponboardingcompose.R
import com.app.organizationapponboardingcompose.navigation.Screen
import com.app.organizationapponboardingcompose.ui.theme.ACTIVE_INDICATOR_COLOR
import com.app.organizationapponboardingcompose.ui.theme.BUTTON_COLOR
import com.app.organizationapponboardingcompose.ui.theme.INACTIVE_INDICATOR_COLOR
import com.app.organizationapponboardingcompose.ui.theme.TEXT_COLOR
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

data class OnboardingItem(val drawable: Int, val title: String, val description: String)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavHostController) {
    val onboardingItems = listOf(
        OnboardingItem(
            R.drawable.welcome,
            "Welcome To Stazam",
            "Easily Organise your day to day activities for Maximum productivity"
        ),
        OnboardingItem(
            R.drawable.alarms, "Set Alarms",
            "Set up Alarms daily for Keeping regular schedules"
        ),
        OnboardingItem(
            R.drawable.event,
            "Create Event Reminder",
            "Set up reminders, that will keep your most important priorities top of mind"
        ),
        OnboardingItem(
            R.drawable.notes,
            "Taking Notes",
            "Taking Notes and making important to-do lists on the go"
        )
    )

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .align(Alignment.Start)
                .padding(start = 16.dp, end = 16.dp),
            painter = painterResource(id = R.drawable.stazam),
            contentDescription = null,
        )
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            count = onboardingItems.size,
            state = pagerState,
        ) { currentPage ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(300.dp),
                    painter = painterResource(id = onboardingItems[currentPage].drawable),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 16.dp, end = 16.dp),
                    text = onboardingItems[currentPage].title,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = TEXT_COLOR
                )

                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, start = 16.dp, end = 16.dp),
                    text = onboardingItems[currentPage].description,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = TEXT_COLOR
                )
            }
        }

        PageIndicator(
            modifier = Modifier
                .height(40.dp)
                .padding(5.dp),
            pageCount = onboardingItems.size,
            currentPage = pagerState.currentPage,
        )
        val context = LocalContext.current
        CenteredTextButton(pagerState, onboardingItems.lastIndex) {
            if (pagerState.currentPage == onboardingItems.lastIndex) {
                finishOnboarding(context = context)
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            } else {
                scope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage + 1
                    )
                }
            }
        }
    }
}


@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorSingleDot(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorSingleDot(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(15.dp)
            .width(15.dp)
            .clip(CircleShape)
            .background(if (isSelected) ACTIVE_INDICATOR_COLOR else INACTIVE_INDICATOR_COLOR)
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CenteredTextButton(page: PagerState, listLastIndex: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
            .height(60.dp)
            .clip(MaterialTheme.shapes.medium),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = BUTTON_COLOR)
    ) {
        Text(
            text = when (page.currentPage) {
                0 -> "Get Started"
                listLastIndex -> "Enter"
                else -> "Next"
            },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .size(20.dp),
            textAlign = TextAlign.Center
        )
    }
}

private fun finishOnboarding(context: Context) {
    val sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("appState", true)
    editor.apply()
}

