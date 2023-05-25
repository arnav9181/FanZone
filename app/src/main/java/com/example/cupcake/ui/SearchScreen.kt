package com.example.cupcake.ui



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource




@Composable
fun SearchScreen(navController: NavHostController = rememberNavController()) {
    var searchText by remember { mutableStateOf("") }




    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            )
        )

        // Replace with your own data source
        val items = DataSource.NBA_teams

        val filteredItems = remember(items, searchText) {
            if (searchText.isBlank()) {
                items
            } else {
                items.filter { it.contains(searchText, ignoreCase = true) }
            }
        }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredItems) { item ->
                    ListItem(
                        item = item,
                        isStarred = remember { mutableStateOf(false) },
                        onStarClick = { isStarred ->
                            isStarred.value = !isStarred.value
                        }
                    )
                }

        }
    }
}

@Composable
fun ListItem(item: String, isStarred: MutableState<Boolean>, onStarClick: (MutableState<Boolean>) -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { onStarClick(isStarred) },
                content = {
                    Icon(
                        imageVector = if (isStarred.value) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = if (isStarred.value) "Starred" else "Not Starred",
                        tint = if (isStarred.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                    )
                }
            )
        }
    }
}

