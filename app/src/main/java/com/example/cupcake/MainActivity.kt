/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


/**
 * Activity for cupcake order flow.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar(navController) },  // New top bar
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "Favorites") {
                composable("Live") {
                    LiveScreen()
                }
                composable("Favorites") {
                    FavoriteScreen()
                }
                composable("Search") {
                    SearchScreen()
                }
            }
        }
    }
}

@Composable
fun TopBar(navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /* Do something when the button is clicked */ }) {
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Icon 1"
                )
            }

            IconButton(onClick = { /* Do something when the button is clicked */ }) {
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Icon 2"
                )
            }

            IconButton(onClick = { /* Do something when the button is clicked */ }) {
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Icon 3"
                )
            }
        }
    }
}

@Composable
fun LiveScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,  // This will evenly distribute the cards on the screen
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Text(text = "Live Updates", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))

        // Team 1 vs Team 2 Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)  // Adjust the height as per your needs
                .padding(vertical = 16.dp)  // Increase vertical padding to increase space between cards
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Team
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Team 1 Logo"
                )
                Text(text = "Score: 2 - 1", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), textAlign = TextAlign.Center)

                // Right Team
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Team 2 Logo"
                )
            }
        }

        // Team 3 vs Team 4 Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Team
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Team 3 Logo"
                )
                Text(text = "Score: 3 - 0", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), textAlign = TextAlign.Center)

                // Right Team
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Team 4 Logo"
                )
            }
        }

        // Team 5 vs Team 6 Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Team
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Team 5 Logo"
                )
                Text(text = "Score: 1 - 1", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), textAlign = TextAlign.Center)

                // Right Team
                Image(
                    painter = painterResource(id = R.drawable.gsw),
                    contentDescription = "Team 6 Logo"
                )
            }
        }
    }
}

@Composable
fun FavoriteScreen() {
    Text(text = "Favorite Teams", modifier = Modifier.padding(16.dp))
}

@Composable
fun SearchScreen() {
    Text(text = "Search For Teams", modifier = Modifier.padding(16.dp))
}



@Composable
fun BottomBar(navController: NavHostController) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.PlayArrow, contentDescription = null) },
            selected = false,
            onClick = { navController.navigate("Live") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Star, contentDescription = null) },
            selected = false,
            onClick = { navController.navigate("Favorites") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Search, contentDescription = null) },
            selected = false,
            onClick = { navController.navigate("Search") }
        )
    }
}