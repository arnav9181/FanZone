package com.example.fanzone.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.fanzone.R
import com.example.fanzone.TopBar
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


@Composable
fun LiveScreen() {
    val viewModel: LiveScreenViewModel = viewModel()
    val isLoading = viewModel.isLoading.value
    val apiResponse = viewModel.apiResponse.value

    LaunchedEffect(Unit) {
        viewModel.makeApiCall("https://site.api.espn.com/apis/site/v2/sports/baseball/mlb/scoreboard")
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar(Title = "Live Games")
        if (isLoading) {
            Text(text = "Loading...")
        } else {
            //Text(text = apiResponse)
            val jsonElement = Gson().fromJson(apiResponse, JsonElement::class.java)

            val games=jsonElement.asJsonObject["events"].asJsonArray //events array

            DisplayEvents(games)
            //Text(text =events.asJsonArray[0].toString())
            //Text(text = "Your Dog is a "+jsonElement.asJsonObject["message"].asJsonObject["bulldog"].asJsonArray[0].asString+" bulldog")
            //Text(text = "Other Bulldog Breeds are "+jsonElement.asJsonObject["message"].asJsonObject["bulldog"].asJsonArray)
        }
    }


}
@Composable
fun DisplayEvents(events: JsonArray) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        items(events.toList()) { event ->
            val eventString = event.asJsonObject["shortName"].asString

            val team1=event.asJsonObject["competitions"].asJsonArray[0].asJsonObject["competitors"].asJsonArray[0].asJsonObject["team"] //Away Team
            val team2=event.asJsonObject["competitions"].asJsonArray[0].asJsonObject["competitors"].asJsonArray[1].asJsonObject["team"] //Home Team

            val team1Logo=team1.asJsonObject["logo"].asString
            val team2Logo=team2.asJsonObject["logo"].asString
            val team1Score=event.asJsonObject["competitions"].asJsonArray[0].asJsonObject["competitors"].asJsonArray[1].asJsonObject["score"].asString
            val team2Score=event.asJsonObject["competitions"].asJsonArray[0].asJsonObject["competitors"].asJsonArray[0].asJsonObject["score"].asString
            //STATUS_SCHEDULED

            var status=event.asJsonObject["competitions"].asJsonArray[0].asJsonObject["status"].asJsonObject["type"].asJsonObject["name"].asString

            var gamecastLink=event.asJsonObject["links"].asJsonArray[0].asJsonObject["href"].asString
            val statusString=event.asJsonObject["competitions"].asJsonArray[0].asJsonObject["status"].asJsonObject["type"].asJsonObject["shortDetail"].asString
            var score=team1Score+" - "+team2Score

            if(status=="STATUS_SCHEDULED"){

                score=""
            }

            println(team1Logo)
            println(team2)


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Team
                    AsyncImage(
                        model = team2Logo,
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp) // Set the desired size here
                    )
                    Text(
                        text = eventString+"\n"+score+"\n"+statusString,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colors.secondary),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            context.startActivity(
                                Intent(Intent.ACTION_VIEW, Uri.parse(gamecastLink))
                            )

                        }
                    )


                    // Right Team
                    AsyncImage(
                        model = team1Logo,
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp) // Set the desired size here
                    )

                }
            }
        }
    }
}


class LiveScreenViewModel : androidx.lifecycle.ViewModel() {
    val isLoading = mutableStateOf(true)
    val apiResponse = mutableStateOf("")

    fun makeApiCall(url:String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                     // Replace with the actual API endpoint
                    URL(url).readText()
                }
                apiResponse.value = response
                isLoading.value = false
            } catch (e: Exception) {
                // Handle any exceptions that occur during the API call
                apiResponse.value = ""
                isLoading.value = false
            }
        }
    }
}



@Composable
fun LiveScreenOld() {
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