package com.example.nutrition_assessment_system_android_app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.navigation.authNavGraph
import com.example.nutrition_assessment_system_android_app.ui.navigation.AppNavHost
import com.example.nutrition_assessment_system_android_app.ui.theme.NutritionassessmentsystemandroidappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutritionassessmentsystemandroidappTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavHost(
                            startDestination = "auth/login",
                        )
                    }
                }
            }
        }
    }
}