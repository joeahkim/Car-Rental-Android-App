package com.joeahkim.carrental.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
            .background(Color(0xFFF5F5F5)) // Light gray background
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Header
            HomeHeader(clientName = homeState.clientName)

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Search Bar
            SearchBar()

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Brands
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(homeState.brands) { brand ->
                    BrandItem(brand = brand)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Popular Car (Top Cars)
            SectionTitle(title = "Popular Car", onSeeAll = onSeeAllTopCars)
            
            Spacer(modifier = Modifier.height(16.dp))

            // Using a flow layout or simply a Column of Rows for the grid effect within a scrollable column
            // Since we are already in a verticalScroll, we can't easily use LazyVerticalGrid.
            // For now, let's use a Column with Rows to simulate a 2-column grid.
            val topCars = homeState.topCars
            val rows = (topCars.size + 1) / 2
            
            for (i in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val firstIndex = i * 2
                    val secondIndex = firstIndex + 1
                    
                    if (firstIndex < topCars.size) {
                        Box(modifier = Modifier.weight(1f)) {
                            val car = topCars[firstIndex]
                             CarCard(
                                car = Car(
                                    id = car.id,
                                    name = car.name,
                                    pricePerDay = "$${car.pricePerDay}",
                                    imageUrl = car.imageUrl
                                ),
                                onClick = { onCarClick(car.id) }
                            )
                        }
                    }
                    
                    if (secondIndex < topCars.size) {
                        Box(modifier = Modifier.weight(1f)) {
                            val car = topCars[secondIndex]
                             CarCard(
                                car = Car(
                                    id = car.id,
                                    name = car.name,
                                    pricePerDay = "$${car.pricePerDay}",
                                    imageUrl = car.imageUrl
                                ),
                                onClick = { onCarClick(car.id) }
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Available Cars Section (Optional based on design, but keeping for functionality)
             if (homeState.availableCars.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(title = "Available Cars", onSeeAll = onSeeAllAvailableCars)
                Spacer(modifier = Modifier.height(16.dp))
                 LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(homeState.availableCars) { car ->
                        AvailableCarCard(car = car, onClick = { onCarClick(car.id.toInt()) })
                    }
                }
            }


            Spacer(modifier = Modifier.height(100.dp)) // Bottom padding for nav bar
        }
    }
}

@Composable
fun HomeHeader(clientName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = CircleShape,
                color = Color.Gray,
                modifier = Modifier.size(50.dp)
            ) {
                // Placeholder for User Avatar
                 Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with actual avatar
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Welcome \uD83D\uDC4B", // Waving hand emoji
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = clientName.ifEmpty { "Guest" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
        
        Surface(
            shape = CircleShape,
            color = Color.White,
            modifier = Modifier.size(40.dp),
            shadowElevation = 2.dp
        ) {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun SearchBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            shadowElevation = 1.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Search your car",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Black, // Dark background for filter button
            modifier = Modifier.size(50.dp)
        ) {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Filter",
                    tint = Color.White
                )
            }
        }
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
            color = Color.Black
        )
        onSeeAll?.let {
            TextButton(onClick = it) {
                Text("View All", color = Color.Gray)
            }
        }
    }
}

@Composable
fun BrandItem(brand: Brand) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(64.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = if (brand.name == "Tesla") Color.Black else Color.White, // Example: Highlight selected or specific brand
            modifier = Modifier.size(64.dp),
            shadowElevation = 0.dp,
            border = if (brand.name != "Tesla") androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)) else null
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (!brand.logoUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = brand.logoUrl,
                        contentDescription = brand.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    Text(
                        text = brand.name.take(2).uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (brand.name == "Tesla") Color.White else Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = brand.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun CarCard(car: Car, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Adjusted height
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                // Heart Icon
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite, // Or FavoriteBorder
                        contentDescription = "Favorite",
                        tint = Color.Black, 
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                // Car Image
                AsyncImage(
                    model = car.imageUrl,
                    contentDescription = car.name,
                    contentScale = ContentScale.Fit, // Fit to show whole car
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Car Name
                Text(
                    text = car.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Price
                    Text(
                        text = car.pricePerDay,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    // Rating
                    Row(verticalAlignment = Alignment.CenterVertically) {
                         Icon(
                            painter = painterResource(id = android.R.drawable.star_on), // Use system star or add one
                            contentDescription = "Rating",
                            tint = Color(0xFFFFD700), // Gold
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "4.5", // Hardcoded for now as per design
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
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
    // Keeping simpler version for horizontal list if needed, or update to match CarCard style
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.width(200.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = car.carImageUrl,
                contentDescription = car.carName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(car.carName, fontWeight = FontWeight.Bold)
            Text("KSh ${car.price}/day", color = Color.Gray, fontSize = 12.sp)
        }
    }
}