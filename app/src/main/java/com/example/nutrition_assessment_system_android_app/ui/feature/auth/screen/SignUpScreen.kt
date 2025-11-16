package com.example.nutrition_assessment_system_android_app.ui.feature.auth.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.composables.icons.lucide.Lock
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import com.composables.icons.lucide.User
import com.example.nutrition_assessment_system_android_app.R
import com.example.nutrition_assessment_system_android_app.ui.common.component.button.PrimaryButton
import com.example.nutrition_assessment_system_android_app.ui.common.component.textfield.CustomOutlinedTextField
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel.AuthIntent
import com.example.nutrition_assessment_system_android_app.ui.feature.auth.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController? = null
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Handle registration success
    LaunchedEffect(uiState) {
        uiState.registrationSuccess?.let { event ->
            // Navigate to home or show success message
//            navController?.navigate(com.example.nutrition_assessment_system_android_app.ui.navigation.NavigationRoutes.Home.HOME) {
//                popUpTo(com.example.nutrition_assessment_system_android_app.ui.navigation.NavigationRoutes.Auth.REGISTER) { inclusive = true }
//            }
            Log.d("SignUpScreen", "Registration successful for user: ${uiState.isAuthenticated}")
            event.onConsumed()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sign up to get started",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Error message
            if (uiState.errorMessage != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Sign Up Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Name Field
                    CustomOutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        icon = Lucide.User,
                        label = "Full Name",
                        keyboardType = KeyboardType.Text
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email Field
                    CustomOutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        icon = Lucide.Mail,
                        label = "Email",
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    CustomOutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        icon = Lucide.Lock,
                        label = "Password",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password Field
                    CustomOutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        icon = Lucide.Lock,
                        label = "Confirm Password",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Sign Up Button
                    PrimaryButton(
                        onClick = {
                            viewModel.onTriggerIntent(
                                AuthIntent.Register(
                                    name = name,
                                    email = email,
                                    password = password,
                                    confirmPassword = confirmPassword
                                )
                            )
                        },
                        text = if (uiState.isLoading) "Signing Up..." else "Sign Up",
                        enabled = !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Divider with Text
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                        Text(
                            text = "OR",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Google Sign Up Button
                    Button(
                        onClick = { /* TODO: Handle Google sign up */ },
                        enabled = !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable._123025_logo_google_g_icon),
                            contentDescription = "Google",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Continue with Google",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Section
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Sign In",
                    modifier = Modifier.clickable {
                        navController?.navigateUp()
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Loading overlay
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}