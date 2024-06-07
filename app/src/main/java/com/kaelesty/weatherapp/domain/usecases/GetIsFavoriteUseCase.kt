package com.kaelesty.weatherapp.domain.usecases

import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.repos.FavoritesRepository
import javax.inject.Inject

class GetIsFavoriteUseCase @Inject constructor(
	private val repo: FavoritesRepository
) {

	operator fun invoke(city: City) = repo.getIsFavorite(city)
}