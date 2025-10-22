package com.joeahkim.carrental.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(modifier: Modifier = Modifier,
                onSignUpClick: () -> Unit,
                onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 60.dp)
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 44.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 100.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                shape = RoundedCornerShape(8.dp),
                label = { Text(text = "Email", fontSize = 24.sp) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(8.dp),
                label = { Text(text = "Password", fontSize = 24.sp) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onLoginSuccess) {
                Text(text = "Sign in", fontSize = 26.sp)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 20.dp)
        ) {
            Text(
                text = "Don't have an account?",
                fontSize = 20.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = onSignUpClick) {
                Text("Sign up")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(onSignUpClick = {}, onLoginSuccess = {})
}