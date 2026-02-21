package com.joeahkim.carrental.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joeahkim.carrental.data.local.DataStoreManager

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    dataStoreManager: DataStoreManager = DataStoreManager(LocalContext.current),
    onEditProfileClick: () -> Unit = { /* TODO: Navigate to Edit Profile */ }
) {
    val client by viewModel.client.collectAsState()
    val error by viewModel.error.collectAsState()
    val token by dataStoreManager.getToken().collectAsState(initial = null)

    LaunchedEffect(token) {
        if (!token.isNullOrEmpty()) {
            viewModel.loadProfile(token!!)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        when {
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                }
            }

            client == null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
            }

            else -> {
                val profile = client!!
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    // Header Background
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .background(Color(0xFF212121))
                    ) {
//                        Text(
//                            text = "My Profile",
//                            style = MaterialTheme.typography.headlineMedium,
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier
//                                .align(Alignment.TopCenter)
//                                .padding(top = 48.dp)
//                        )
                        
                        Surface(
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.BottomCenter)
                                .offset(y = 60.dp),
                            shape = CircleShape,
                            shadowElevation = 4.dp,
                            color = Color.White
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile Picture",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(70.dp))
                    
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = profile.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = profile.email,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }

                        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))

                        // Details Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                ProfileDetailRow(label = "Phone", value = profile.phone_number.toString())
                                ProfileDetailRow(label = "ID Number", value = profile.id_number.toString())
                                ProfileDetailRow(label = "Nationality", value = profile.nationality)
                                ProfileDetailRow(label = "Residence", value = profile.residence)
                                ProfileDetailRow(label = "Occupation", value = profile.occupation)
                                ProfileDetailRow(label = "Company", value = profile.company ?: "Not specified")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = onEditProfileClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Edit Profile", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(100.dp)) // Bottom padding
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}