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
package com.example.fanzone

import UserStorage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fanzone.ui.FavScreen
import com.example.fanzone.ui.LiveScreen
import com.example.fanzone.ui.SearchScreen


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
    val dataRepository = UserStorage(context = LocalContext.current)
    val navController = rememberNavController()
    Scaffold(

        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "Favorites") {
                composable("Live") {
                    LiveScreen()
                }
                composable("Favorites") {
                    FavScreen(dataRepository)
                }
                composable("Search") {
                    SearchScreen(dataRepository,navController = navController)
                }
            }
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


@Composable
fun TopBar(Title: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = Title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}


