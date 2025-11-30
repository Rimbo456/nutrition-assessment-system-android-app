package com.example.nutrition_assessment_system_android_app.ui.feature.home.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.nutrition_assessment_system_android_app.ui.common.component.bar.BottomTabBar
import com.example.nutrition_assessment_system_android_app.ui.feature.overview.screen.OverviewScreen
import com.example.nutrition_assessment_system_android_app.ui.feature.chat.screen.PreChatScreen
import com.example.nutrition_assessment_system_android_app.ui.feature.home.viewmodel.HomeIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.home.viewmodel.HomeViewModel
import com.example.nutrition_assessment_system_android_app.ui.feature.profile.screen.ProfileScreen
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    navigateToOnBoarding: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.onTriggerIntent(HomeIntent.CheckInformationUser)
    }

    LaunchedEffect(uiState) {
        Log.d("HomeScreen", "UI State changed: $uiState")

        uiState.navigateToOnBoarding?.let { event ->
            navigateToOnBoarding()
            event.onConsumed()
        }
        uiState.showLoginSuccessToast?.let { event ->
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
            event.onConsumed()
        }
    }

    Scaffold(
        bottomBar = {
            BottomTabBar(
                pagerState = pagerState,
                onTabSelected = { index ->
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                navigateToCamera = { navController.navigate("camera") }
            )
        }
    ) {
        HorizontalPager(
            count = 4,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> {
                    OverviewScreen()
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
                3 -> {
                    ProfileScreen()
                }
            }
        }
    }
}