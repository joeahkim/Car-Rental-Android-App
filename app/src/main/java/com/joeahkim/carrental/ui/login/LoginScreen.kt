package com.joeahkim.carrental.ui.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import android.util.Patterns
import androidx.compose.ui.unit.dp
import org.json.JSONObject
import retrofit2.HttpException
import androidx.compose.ui.unit.sp
import com.joeahkim.carrental.R
import com.joeahkim.carrental.data.local.DataStoreManager
import com.joeahkim.carrental.data.model.LoginRequest
import com.joeahkim.carrental.data.remote.RetrofitInstance
import com.joeahkim.carrental.ui.theme.Pink40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sin

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val amplitude = 16f
            val frequency = 0.02f

            val lineFractions = listOf(0.2f, 0.4f, 0.6f)
            for (frac in lineFractions) {
                val baseX = w * frac
                val path = Path().apply {
                    moveTo(baseX + amplitude * sin(0.0).toFloat(), 0f)
                    var y = 0f
                    while (y <= h) {
                        lineTo(baseX + amplitude * sin((y * frequency).toDouble()).toFloat(), y)
                        y += 1f
                    }
                }
                drawPath(path, Pink40.copy(alpha = 0.22f), style = Stroke(width = 2f))
            }

            val boundaryX = w * 0.75f

            val fillPath = Path().apply {
                moveTo(w, 0f)
                lineTo(boundaryX + amplitude * sin(0.0).toFloat(), 0f)
                var y = 0f
                while (y <= h) {
                    lineTo(boundaryX + amplitude * sin((y * frequency).toDouble()).toFloat(), y)
                    y += 1f
                }
                lineTo(w, h)
                close()
            }
            drawPath(fillPath, Pink40.copy(alpha = 0.13f), style = Fill)

            val edgePath = Path().apply {
                moveTo(boundaryX + amplitude * sin(0.0).toFloat(), 0f)
                var y = 0f
                while (y <= h) {
                    lineTo(boundaryX + amplitude * sin((y * frequency).toDouble()).toFloat(), y)
                    y += 1f
                }
            }
            drawPath(edgePath, Pink40, style = Stroke(width = 3f))
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .padding(start = 24.dp, top = 48.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Car Rentals Logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Car",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 50.sp
                )
                Text(
                    text = "Rentals",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Pink40,
                    lineHeight = 50.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    label = { Text("Email") },
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = emailError != null,
                    supportingText = emailError?.let { { Text(it, color = Color.Red) } },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible)
                                    Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible)
                                    "Hide password" else "Show password"
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val trimmedEmail = email.trim()
                        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
                            emailError = "Please enter a valid email address"
                            return@Button
                        }
                        isLoading = true
                        errorMessage = null

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val response = RetrofitInstance.api.login(LoginRequest(email, password))

                                dataStoreManager.saveUserData(
                                    token = "Bearer ${response.token}",
                                    name  = response.client?.name ?: "",
                                    id    = response.client?.id.toString()
                                )

                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                    onLoginSuccess()
                                }
                            } catch (e: HttpException) {
                                val errorBody = e.response()?.errorBody()?.string()
                                val apiMessage = try {
                                    errorBody?.let { JSONObject(it).getString("message") }
                                } catch (_: Exception) { null }
                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                    errorMessage = apiMessage ?: "Login failed"
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    isLoading = false
                                    errorMessage = e.localizedMessage ?: "An unexpected error occurred"
                                }
                            }
                        }
                    }
                ) {
                    Text(text = if (isLoading) "Signing in..." else "Sign in")
                }
                errorMessage?.let {
                    Text(text = it, color = Color.Red, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text("Don't have an account?")
                TextButton(onClick = onSignUpClick) { Text("Sign up") }
            }
        }
    }
}
