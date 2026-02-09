package com.joeahkim.carrental.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.joeahkim.carrental.R
import com.joeahkim.carrental.domain.model.AvailableCars
import com.joeahkim.carrental.domain.model.Brand

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    clientName: String = "",
    onSeeAllTopCars: () -> Unit = {},
    onSeeAllAvailableCars: () -> Unit = {},
    onCarClick: (Int) -> Unit = {},
    onOfferClick: () -> Unit = {}
) {

    val homeState = viewModel.uiState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hi, ${homeState.clientName}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Find your perfect ride today",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(title = "Car Brands")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(homeState.brands) { brand ->
                BrandItem(brand = brand)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(title = "Top Cars", onSeeAll = onSeeAllTopCars)

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(homeState.topCars, key = { it.id }) { car ->
                CarCard(
                    car = Car(
                        id = car.id,
                        name = car.name,
                        pricePerDay = "KSh ${car.pricePerDay}",
                        imageUrl = car.imageUrl
                    ),
                    onClick = { onCarClick(car.id) }
                )
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(title = "Available Cars", onSeeAll = onSeeAllAvailableCars)

        when {
            homeState.isLoading -> {
                LoadingAvailableCars()
            }
            homeState.error != null -> {
                ErrorState(message = homeState.error)
            }
            homeState.availableCars.isEmpty() -> {
                EmptyAvailableCars()
            }
            else -> {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(homeState.availableCars, key = { it.id }) { car ->
                        AvailableCarCard(car = car, onClick = { onCarClick(car.id.toInt()) })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(title = "Special Offers")
        OfferCard(
            title = "Limited Time Offer!",
            subtitle = "Get 20% off on all SUVs this week",
            onClick = onOfferClick
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SectionTitle(title: String, onSeeAll: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        onSeeAll?.let {
            TextButton(onClick = it) {
                Text("See all", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun BrandItem(brand: Brand) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 4.dp,
        modifier = Modifier.size(80.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (!brand.logoUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = brand.logoUrl,
                    contentDescription = brand.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                Text(
                    text = brand.name.take(2).uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarCard(car: Car, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .width(240.dp)
            .height(200.dp)
    ) {
        Column {
            AsyncImage(
                model = car.imageUrl,
                contentDescription = car.name,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground), // create this drawable
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                fallback = painterResource(id = R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = car.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${car.pricePerDay}/day",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun OfferCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                )
            }
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ShoppingCart,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(56.dp)
            )
        }
    }
}

data class Car(
    val id: Int,
    val name: String,
    val pricePerDay: String,
    val imageUrl: String? = null
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableCarCard(car: AvailableCars, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.width(260.dp)
    ) {
        Column {
            AsyncImage(
                model = car.carImageUrl,
                contentDescription = car.carName,
                placeholder = painterResource(R.drawable.ic_launcher_foreground), // add a placeholder
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = car.carName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "KSh ${car.price}/day",  // or "$ ${car.price}"
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Optional: Add rating or features later
            }
        }
    }
}

@Composable
fun LoadingAvailableCars() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(5) {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(260.dp)
                    .height(220.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun EmptyAvailableCars() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No cars available at the moment",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ErrorState(message: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message ?: "Something went wrong",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}