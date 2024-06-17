package com.kaelesty.weatherapp.presentation.favorites.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddCircleOutline
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kaelesty.weatherapp.presentation.favorites.FavoritesComponent
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore

@Composable
fun FavoritesContent(
	component: FavoritesComponent
) {
	val state by component.model.collectAsState()

	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
		FloatingActionButton(
			onClick = {
				component.onSearchCity(onCitySelected = FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected.ShowForecast)
			},
			modifier = Modifier
				.padding(32.dp)
				.size(60.dp)
		) {
			Icon(Icons.Sharp.Search, contentDescription = null)
		}
	}

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
		item {
			Card(
				modifier = Modifier
					.padding(6.dp)
					.clickable {
						component.onSearchCity(
							onCitySelected = FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected.AddToFavorite
						)
					}
			) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.background(
							brush = Brush.linearGradient(listOf(Color.Magenta, Color.Yellow))
						)
						.padding(8.dp),
					horizontalAlignment = Alignment.Start
				) { Box(
					modifier = Modifier.height(170.dp),
					contentAlignment = Alignment.Center
				) {
						Icon(
							Icons.Sharp.AddCircleOutline,
							contentDescription = null,
							modifier = Modifier
								.padding(30.dp)
								.fillMaxSize(),
							tint = Color.White
						)
					}
				}
			}
		}
	}
}