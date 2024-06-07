package com.kaelesty.weatherapp.domain.usecases

import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.repos.FavoritesRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
	private val repo: FavoritesRepository
) {

	suspend operator fun invoke(city: City) = repo.addToFavorites(city)
}