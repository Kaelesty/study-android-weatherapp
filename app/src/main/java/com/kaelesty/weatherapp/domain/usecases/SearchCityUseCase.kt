package com.kaelesty.weatherapp.domain.usecases

import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.repos.FavoritesRepository
import retrofit2.http.Query
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
	private val repo: FavoritesRepository
) {

	suspend operator fun invoke(query: String) = repo.searchCity(query)
}