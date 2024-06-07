package com.kaelesty.weatherapp.domain.usecases

import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.repos.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
	private val repo: WeatherRepository
) {

	suspend operator fun invoke(city: City) = repo.getCurrentWeather(city)
}