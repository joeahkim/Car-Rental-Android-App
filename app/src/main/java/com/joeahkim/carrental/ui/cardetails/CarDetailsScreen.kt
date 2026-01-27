package com.joeahkim.carrental.ui.cardetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.joeahkim.carrental.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailsScreen(
    carId: Int,
    onBack: () -> Unit,
    viewModel: CarDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(carId) {
        viewModel.loadCarDetails(carId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Car Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (uiState.carDetail != null) {
                Button(
                    onClick = { /* book car later */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Book This Car - KSh ${uiState.carDetail.pricePerDay}/day")
                }
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = uiState.error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadCarDetails(carId) }) {
                            Text("Retry")
                        }
                    }
                }
            }

            uiState.carDetail != null -> {
                CarDetailsContent(
                    carDetail = uiState.carDetail,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun CarDetailsContent(
    carDetail: com.joeahkim.carrental.domain.model.CarDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Car Image
        AsyncImage(
            model = carDetail.imageUrl,
            contentDescription = "${carDetail.make} ${carDetail.model}",
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        // Car Details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "${carDetail.make} ${carDetail.model}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "KSh ${carDetail.pricePerDay}/day",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    DetailRow(label = "Make", value = carDetail.make)
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Model", value = carDetail.model)
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Number Plate", value = carDetail.numberPlate)
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Price per Day", value = "KSh ${carDetail.pricePerDay}")
                }
            }

            Spacer(modifier = Modifier.height(100.dp)) // Space for bottom button
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}