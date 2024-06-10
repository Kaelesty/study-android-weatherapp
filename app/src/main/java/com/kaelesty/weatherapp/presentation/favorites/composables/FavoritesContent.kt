package com.kaelesty.weatherapp.presentation.favorites.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaelesty.weatherapp.presentation.favorites.FavoritesComponent

@Composable
fun FavoritesContent(
	component: FavoritesComponent
) {
	val state by component.model.collectAsState()

	LazyVerticalGrid(columns = GridCells.Fixed(2)) {
		items(state.cities, key = { it.city.id }) {
			CityCard(
				city = it.city,
				weather = it.weather,
				modifier = Modifier
					.padding(6.dp)
					.clickable {
						component.onShowCityForecast(it.city)
					}

			)
			SideEffect {
				if (it.weather == null) {
					component.onLoadCityWeather(city = it.city)
				}
			}
		}
	}
}