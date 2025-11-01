package com.joeahkim.carrental.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joeahkim.carrental.data.local.DataStoreManager

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    dataStoreManager: DataStoreManager = DataStoreManager(LocalContext.current)
) {
    val client by viewModel.client.collectAsState()
    val error by viewModel.error.collectAsState()
    val token by dataStoreManager.getToken().collectAsState(initial = null)

    LaunchedEffect(token) {
        if (!token.isNullOrEmpty()) {
            println("DEBUG: Using token = $token")
            viewModel.loadProfile(token!!)
        }
    }

    when {
        error != null -> Text(text = "Error: $error")
        client == null -> CircularProgressIndicator()
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Name: ${client!!.name}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Email: ${client!!.email}")
                Text(text = "Phone: ${client!!.phone_number}")
                Text(text = "ID Number: ${client!!.id_number}")
                Text(text = "Nationality: ${client!!.nationality}")
                Text(text = "Residence: ${client!!.residence}")
                Text(text = "Occupation: ${client!!.occupation}")
                Text(text = "Company: ${client!!.company}")

                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { /* TODO: navigate to EditProfile */ }) {
                    Text("Edit Profile")
                }
            }
        }
    }
}
