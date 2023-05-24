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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import com.example.cupcake.data.DataSource.quantityOptions
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectQuantityButton

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
fun App(viewModel: OrderViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "Search") {
                composable("Live") {
                    LiveScreen()
                }
                composable("Favorites") {
                    FavoriteScreen()
                }
                composable("Search") {
                    SearchScreen(navController = navController)
                }
            }
        }
    }
}


@Composable
fun LiveScreen() {
    Text(text = "Live Updates", modifier = Modifier.padding(16.dp))
}
@Composable
fun FavoriteScreen() {
    Text(text = "Favorite Teams", modifier = Modifier.padding(16.dp))
}

@Composable
fun SearchScreen( navController: NavHostController = rememberNavController()) {
    Text(text = "Search For Teams", modifier = Modifier.padding(16.dp))

        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Select a Team", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(8.dp))
            quantityOptions.forEach { item ->
                SelectQuantityButton(
                    labelResourceId = item.first,
                    onClick = {
                        navController.navigate("Favorites")
                    }
                )
            }
        }
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