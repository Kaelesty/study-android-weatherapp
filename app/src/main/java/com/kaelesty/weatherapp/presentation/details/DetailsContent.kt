package com.kaelesty.weatherapp.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun DetailsContent(
	component: DetailsComponent
) {

	val state by component.model.collectAsState()

	Column {
		Text(text = state.city.name + " " + state.city.country)
		state.weather?.let {
			Text("${it.temp} (${it.desc})")
		}
		state.forecast?.let {
			it.upcoming.forEachIndexed { index, it ->
				Text("$index ${it.temp} (${it.desc})")
			}
		}
	}
}