// ui/home/HomeScreen.kt
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joeahkim.carrental.R

@Composable
fun HomeScreen(
    clientName: String = "Joeahkim",
    onSeeAllTopCars: () -> Unit = {},
    onSeeAllAvailableCars: () -> Unit = {},
    onCarClick: (Car) -> Unit = {},
    onOfferClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()           // Edge-to-edge ready
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hi, $clientName 👋",
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
            items(dummyBrands) { brand ->
                BrandItem(brand = brand)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(title = "Top Cars", onSeeAll = onSeeAllTopCars)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(dummyTopCars) { car ->
                CarCard(car = car, onClick = { onCarClick(car) })
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(title = "Available Cars", onSeeAll = onSeeAllAvailableCars)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(dummyAvailableCars) { car ->
                CarCard(car = car, onClick = { onCarClick(car) })
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Special Offers
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
fun BrandItem(brand: CarBrand) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 4.dp,
        modifier = Modifier.size(80.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = brand.name.take(2).uppercase(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
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

// Dummy Data (replace later with ViewModel)
data class CarBrand(val name: String)
data class Car(
    val id: Int,
    val name: String,
    val pricePerDay: String,
    val imageUrl: String? = null
)

private val dummyBrands = listOf(
    CarBrand("Tesla"), CarBrand("BMW"), CarBrand("Mercedes"),
    CarBrand("Audi"), CarBrand("Toyota"), CarBrand("Honda")
)

private val dummyTopCars = listOf(
    Car(1, "Tesla Model 3", "$80"),
    Car(2, "Porsche 911", "$250"),
    Car(3, "Lamborghini Huracan", "$450"),
    Car(4, "BMW M4", "$180"),
    Car(5, "Audi RS7", "$200")
)

private val dummyAvailableCars = listOf(
    Car(6, "Toyota Camry", "$45"),
    Car(7, "Honda Civic", "$40"),
    Car(8, "Mercedes E-Class", "$120"),
    Car(9, "BMW X5", "$150"),
    Car(10, "Range Rover", "$300")
)