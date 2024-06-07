package com.kaelesty.weatherapp.domain.usecases

import com.kaelesty.weatherapp.domain.repos.FavoritesRepository
import javax.inject.Inject

class GetFavoriteCitiesUseCase @Inject constructor(
	private val repo: FavoritesRepository
) {

	operator fun invoke() = repo.getFavoriteCities()
}