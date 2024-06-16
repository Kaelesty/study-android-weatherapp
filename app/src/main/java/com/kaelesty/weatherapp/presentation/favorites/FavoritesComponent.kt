package com.kaelesty.weatherapp.presentation.favorites

import com.kaelesty.weatherapp.domain.entities.City
import kotlinx.coroutines.flow.StateFlow

interface FavoritesComponent {

	val model: StateFlow<FavoritesStore.State>

	fun onAddNewFavoriteCity()

	fun onShowCityForecast(city: City)

	fun onSearchCity(onCitySelected: FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected)

	fun onLoadCityWeather(city: City)
}