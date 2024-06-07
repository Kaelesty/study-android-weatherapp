package com.kaelesty.weatherapp.domain.repos

import com.kaelesty.weatherapp.domain.entities.City
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

	fun getFavoriteCities(): Flow<List<City>>

	fun getIsFavorite(city: City): Flow<Boolean>

	suspend fun addToFavorites(city: City)

	suspend fun removeFromFavorites(cityId: City)

	suspend fun searchCity(query: String): List<City>
}