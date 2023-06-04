package com.example.fanzone.ui

import UserStorage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.fanzone.data.DataSource.teamAbbreviations
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun FavScreen(dataRepository: UserStorage) {
    val viewModel: FavScreenViewModel = viewModel()
    val isLoading = viewModel.isLoading.value
    val apiResponse = viewModel.apiResponse.value
    //val favoriteTeams: List<String> = listOf("atl", "nyy", "lad", "ari")
    val stringList = remember { dataRepository.getStringList() }
    val mappedStrings = stringList.sorted().map { teamName ->
        teamAbbreviations[teamName] ?: ""
    }
    val baseUrl = "https://site.api.espn.com/apis/site/v2/sports/baseball/mlb/teams/"
    val currentIndex = remember { mutableStateOf(0) }
    val favoriteTeamData: MutableMap<String, MutableMap<String, String>> = remember { mutableMapOf() }

    LaunchedEffect(Unit) {
        if (currentIndex.value < mappedStrings.size) {
            //val teamAbb=teamAbbreviations[currentIndex.value]
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
        teamData["record"] = jsonElement["record"].asJsonObject["items"].asJsonArray
            .firstOrNull { it.asJsonObject["description"].asString == "Overall Record" }
            ?.asJsonObject?.get("summary").toString()

        // Extracting next event and short name
        val nextEventArray = jsonElement["nextEvent"].asJsonArray
        if (nextEventArray.size() > 0) {
            val nextEvent = nextEventArray[0].asJsonObject
            val dateStr = nextEvent["date"].asString
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH)
            inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Make sure to interpret the input as a UTC timestamp
            val outputFormat = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(dateStr)
            val formattedDateStr = outputFormat.format(date)
            teamData["nextEventDate"] = formattedDateStr
            teamData["nextEventName"] = nextEvent["shortName"].asString
        }


        // Extracting team logo
        val logoUrl = jsonElement["logos"].asJsonArray[0].asJsonObject["href"].asString
        teamData["logoUrl"] = logoUrl

        favoriteTeamData[jsonElement["abbreviation"].asString.lowercase()] = teamData
        currentIndex.value++
        if (currentIndex.value < mappedStrings.size) {
            viewModel.makeApiCall(baseUrl + mappedStrings[currentIndex.value])
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (favoriteTeamData.isEmpty()) {
            Text(text = "Loading...", style = MaterialTheme.typography.h5)
        } else {
            for (team in mappedStrings) {
                if (favoriteTeamData[team] != null) {
                    Card(shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val imageUrl = favoriteTeamData[team]?.get("logoUrl")!!
                            val imagePainter = rememberAsyncImagePainter(model = imageUrl)
                            Image(
                                painter = imagePainter,
                                contentDescription = "${favoriteTeamData[team]?.get("teamName")!!} Logo",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = favoriteTeamData[team]?.get("teamName")!!, style = MaterialTheme.typography.h5)
                                Text(text = "Overall Record: ${favoriteTeamData[team]?.get("record")}", style = MaterialTheme.typography.body1)
                                Text(text = "Next Event: ${favoriteTeamData[team]?.get("nextEventDate")}, " +
                                        "${favoriteTeamData[team]?.get("nextEventName")}", style = MaterialTheme.typography.body1)

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
