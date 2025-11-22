package com.example.nutrition_assessment_system_android_app.ui.feature.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nutrition_assessment_system_android_app.ui.common.component.bar.BottomTabBar
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen.PreChatScreen
import com.example.nutrition_assessment_system_android_app.ui.feature.home.component.DailyIntakeCard
import com.example.nutrition_assessment_system_android_app.ui.feature.home.component.StatisticsCard
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomTabBar(
                pagerState = pagerState,
                onTabSelected = { index ->
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> {
                    Column {
                        DailyIntakeCard()
                        StatisticsCard()
                        StatisticsCard()
                        StatisticsCard()
                    }
                }
                1 -> {
                    // Second tab content
                }
                2 -> {
                    PreChatScreen(
                        onStartChat = { sessionId ->
                            navController.navigate("chat")
                        }
                    )
                }
            }
        }
    }
}