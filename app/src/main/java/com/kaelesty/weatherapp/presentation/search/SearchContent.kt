package com.kaelesty.weatherapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchContent(
    component: SearchComponent
) {

    val state by component.model.collectAsState()

    Column {
        TextField(value = state.query, onValueChange = {
            component.setQuery(it)
        })

        if (state.isLoading) {
            CircularProgressIndicator()
        }
        else {
            LazyColumn {
                items(
                    items = state.results,
                    key = { it.id }
                ) {
                    Card(
                        onClick = { component.selectCity(it) },
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Text(text = "${it.name}, ${it.country}", fontSize = 24.sp, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}