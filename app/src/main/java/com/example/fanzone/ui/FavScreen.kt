package com.example.fanzone.ui

import UserStorage
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.fanzone.data.DataSource.teamAbbreviations
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun FavScreen(dataRepository: UserStorage) {
    val context = LocalContext.current
    val viewModel: FavScreenViewModel = viewModel()
    val isLoading = viewModel.isLoading.value
    val apiResponse = viewModel.apiResponse.value
    val stringList = remember { dataRepository.getStringList() }
    val mappedStrings = stringList.sorted().map { teamName ->
        teamAbbreviations[teamName] ?: ""
    }
    print(mappedStrings.toString())
    val baseUrl = "https://site.api.espn.com/apis/site/v2/sports/baseball/mlb/teams/"
    val currentIndex = remember { mutableStateOf(0) }
    val favoriteTeamData: MutableMap<String, MutableMap<String, String>> = remember { mutableMapOf() }

    LaunchedEffect(Unit) {
        if (currentIndex.value < mappedStrings.size) {
            viewModel.makeApiCall(baseUrl + mappedStrings[currentIndex.value])
        }
    }

    val responseJson = Gson().fromJson(
        apiResponse,
        JsonElement::class.java
    )

    if (responseJson != null){
        val teamData: MutableMap<String, String> = mutableMapOf()
        val jsonElement = responseJson.asJsonObject["team"].asJsonObject
        teamData["teamName"] = jsonElement["displayName"].asString

        val recordDataArray = jsonElement["record"].asJsonObject["items"].asJsonArray
        val overallRecordData = recordDataArray
            .firstOrNull { it.asJsonObject["description"].asString == "Overall Record" }
        if (overallRecordData != null) {
            teamData["record"] = overallRecordData.asJsonObject.get("summary").toString().replace("\"", "")
            overallRecordData.asJsonObject["stats"].asJsonArray.forEach { statElement ->
                val statName = statElement.asJsonObject["name"].asString
                when (statName) {
                    "leagueWinPercent" -> {
                        val leagueWinPercent = statElement.asJsonObject["value"].asDouble * 100
                        teamData["leagueWinPercent"] = String.format("%.4s", leagueWinPercent)
                    }
                    "divisionWinPercent" -> {
                        val divisionWinPercent = statElement.asJsonObject["value"].asDouble * 100
                        teamData["divisionWinPercent"] = String.format("%.4s", divisionWinPercent)
                    }                }
            }
        }

        val nextEventArray = jsonElement["nextEvent"].asJsonArray
        if (nextEventArray.size() > 0) {
            val nextEvent = nextEventArray[0].asJsonObject
            val dateStr = nextEvent["date"].asString
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH)
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(dateStr)
            val formattedDateStr = outputFormat.format(date)
            teamData["nextEventDate"] = formattedDateStr
            teamData["nextEventName"] = nextEvent["shortName"].asString
        }

        val logoUrl = jsonElement["logos"].asJsonArray[0].asJsonObject["href"].asString
        teamData["logoUrl"] = logoUrl

        val rosterLink = jsonElement["links"].asJsonArray
            .firstOrNull { it.asJsonObject["rel"].asJsonArray.contains(JsonPrimitive("roster")) }
            ?.asJsonObject?.get("href")?.asString
        if (rosterLink != null) {
            teamData["rosterLink"] = rosterLink
        }

        favoriteTeamData[jsonElement["abbreviation"].asString.lowercase()] = teamData
        currentIndex.value++
        if (currentIndex.value < mappedStrings.size) {
            viewModel.makeApiCall(baseUrl + mappedStrings[currentIndex.value])
        }
    }

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        if (favoriteTeamData.isEmpty()) {
            item {
                Text(text = "No Favorite Teams Found", style = MaterialTheme.typography.h5)
            }
        } else {
            items(mappedStrings) { team: String ->
                favoriteTeamData[team]?.let { teamData ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(10.dp),
                        elevation = 8.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            val imageUrl = teamData["logoUrl"]!!
                            val imagePainter = rememberImagePainter(data = imageUrl)
                            Image(
                                painter = imagePainter,
                                contentDescription = "${teamData["teamName"]!!} Logo",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = teamData["teamName"]!!,
                                    style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.secondary)
                                )
                                Text(
                                    text = "Overall Record: ${teamData["record"]}",
                                    style = MaterialTheme.typography.body1,
                                )
                                teamData["leagueWinPercent"]?.let {
                                    Text(
                                        text = "League Win Percent: $it%",
                                        style = MaterialTheme.typography.body1,
                                    )
                                }
                                teamData["divisionWinPercent"]?.let {
                                    Text(
                                        text = "Division Win Percent: $it%",
                                        style = MaterialTheme.typography.body1,
                                    )
                                }
                                Text(
                                    text = "Next Event: ${teamData["nextEventDate"]}, ${teamData["nextEventName"]}",
                                    style = MaterialTheme.typography.body1
                                )
                                val rosterLink = teamData["rosterLink"]
                                if (rosterLink != null) {
                                    Text(
                                        text = "Roster",
                                        modifier = Modifier.clickable {
                                            context.startActivity(
                                                Intent(Intent.ACTION_VIEW, Uri.parse(rosterLink))
                                            )
                                        },
                                        style = MaterialTheme.typography.h5.copy(color = Color.Black)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}





class FavScreenViewModel : androidx.lifecycle.ViewModel() {
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
