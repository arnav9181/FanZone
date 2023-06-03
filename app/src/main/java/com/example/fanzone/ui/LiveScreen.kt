package com.example.fanzone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
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
        viewModel.makeApiCall("https://dog.ceo/api/breeds/list/all")
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isLoading) {
            Text(text = "Loading...")
        } else {
            Text(text = apiResponse)
            val jsonElement = Gson().fromJson(apiResponse, JsonElement::class.java)
            Text(text = "Status: "+jsonElement.asJsonObject["status"].asString)
            Text(text = "Your Dog is a "+jsonElement.asJsonObject["message"].asJsonObject["bulldog"].asJsonArray)
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

