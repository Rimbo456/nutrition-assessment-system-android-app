package com.example.nutrition_assessment_system_android_app.ui.common.component.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel.AuthViewModel

@Composable
fun SplashScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoading) {
        // Đợi check xong rồi mới navigate
        Log.d("SplashScreen", "isAuthenticated: ${uiState.isAuthenticated}")
        Log.d("SplashScreen", "Loading: ${uiState.isLoading}")
        if (!uiState.isLoading) {
            if (uiState.isAuthenticated) {
                navigateToHome()
            } else {
                navigateToLogin()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}