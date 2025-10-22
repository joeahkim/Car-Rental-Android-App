package com.joeahkim.carrental.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joeahkim.carrental.R

@Composable
fun HomeScreen() {
    val clientName = "Gakuru"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            Text(
                text = "Hi, $clientName",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A0E21),
                modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
            )
        }

        item {
            Text(
                text = "Car Brands",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(6) {
                    BrandIconPlaceholder()
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Top Cars Section
        item {
            SectionHeader(title = "Top Cars", onSeeAllClick = { /*TODO*/ })
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(5) {
                    CarCardPlaceholder(title = "Tesla Model 3", price = "$80/day")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Available Cars Section
        item {
            SectionHeader(title = "Available Cars", onSeeAllClick = { /*TODO*/ })
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(5) {
                    CarCardPlaceholder(title = "BMW X5", price = "$65/day")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            SectionHeader(title = "Special Offers", onSeeAllClick = { /*TODO*/ })
            OfferCardPlaceholder()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        TextButton(onClick = onSeeAllClick) {
            Text(text = "See all", color = Color(0xFF1E88E5), fontSize = 14.sp)
        }
    }
}

@Composable
fun BrandIconPlaceholder() {
    Box(
        modifier = Modifier
            .size(70.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Placeholder icon
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // temporary icon
            contentDescription = "Car Brand",
            tint = Color.Gray,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun CarCardPlaceholder(title: String, price: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(220.dp)
            .height(180.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Placeholder car image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = price, color = Color(0xFF1E88E5), fontSize = 14.sp)
        }
    }
}

@Composable
fun OfferCardPlaceholder() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5)),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Limited Offer!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Get 20% off on all SUVs this week",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}