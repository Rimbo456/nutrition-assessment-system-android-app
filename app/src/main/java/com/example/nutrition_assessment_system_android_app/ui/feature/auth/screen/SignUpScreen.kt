package com.example.nutrition_assessment_system_android_app.ui.feature.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lock
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import com.composables.icons.lucide.User
import com.example.nutrition_assessment_system_android_app.ui.component.button.PrimaryButton
import com.example.nutrition_assessment_system_android_app.ui.component.textfield.CustomOutlinedTextField

@Composable
fun SignUpScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create a new account"
        )
        CustomOutlinedTextField(
            icon = Lucide.Mail,
            label = "Email"
        )
        CustomOutlinedTextField(
            icon = Lucide.User,
            label = "Name"
        )
        CustomOutlinedTextField(
            icon = Lucide.Lock,
            label = "Password"
        )
        PrimaryButton(
            onClick = { /* TODO: Handle login action */ },
            text = "Sign Up",
            modifier = Modifier.padding(top = 16.dp)
        )
        Row {
            Text(
                text = "Already have an account? ",
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Login",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}