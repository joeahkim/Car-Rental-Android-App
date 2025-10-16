package com.joeahkim.carrental.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlin.math.round

@Composable
fun MainHome(){
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding).height(60.dp)) {
                HomeNavGraph(navController = navController)
            }
        }

}

@Preview
@Composable
fun HomePreview(){
    MainHome()
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Messages,
        BottomNavItem.Profile
    )

    NavigationBar(modifier = Modifier.padding(bottom = 4.dp, start = 4.dp, end = 4.dp)
        .clip(RoundedCornerShape(14.dp)),
        containerColor = Color(0xFF0A0E21),
        tonalElevation = 10.dp
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination

        items.forEach { item ->
            val isSelected = currentDestination?.route == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .size(48.dp)
                                .background(
                                    color = Color(0xFF1D1E33),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = Color.Gray,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 12.sp
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Favorites.route) { FavoritesScreen() }
        composable(BottomNavItem.Messages.route) { MessagesScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
    }


@Composable
fun ProfileScreen() {
    TODO("Not yet implemented")
}

@Composable
fun MessagesScreen() {
    TODO("Not yet implemented")
}

@Composable
fun FavoritesScreen() {
    TODO("Not yet implemented")
}

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Favorites : BottomNavItem("favorites", "Saved", Icons.Default.Favorite)
    object Messages : BottomNavItem("messages", "Messages", Icons.Default.Email)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
}

