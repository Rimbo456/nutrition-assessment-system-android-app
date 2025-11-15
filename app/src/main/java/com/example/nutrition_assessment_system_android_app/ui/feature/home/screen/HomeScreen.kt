package com.example.nutrition_assessment_system_android_app.ui.feature.home.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.nutrition_assessment_system_android_app.ui.component.bar.BottomTabBar
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val pagerState = rememberPagerState(initialPage = 0)

    Scaffold(
        bottomBar = {
            BottomTabBar(
                pagerState = pagerState,
                onTabSelected = { index ->
                    //TODO
                }
            )
        }
    ) {
        //Content
    }
}