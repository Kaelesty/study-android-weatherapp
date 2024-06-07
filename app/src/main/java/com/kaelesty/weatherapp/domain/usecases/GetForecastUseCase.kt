package com.kaelesty.weatherapp.domain.usecases

import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.repos.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
	private val repo: WeatherRepository
) {

	suspend operator fun invoke(city: City, days: Int = 3) = repo.getForecast(city, days)
}