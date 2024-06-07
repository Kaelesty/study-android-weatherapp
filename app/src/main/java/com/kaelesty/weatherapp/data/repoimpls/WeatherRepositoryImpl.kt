package com.kaelesty.weatherapp.data.repoimpls

import com.kaelesty.weatherapp.data.mappers.WeatherMapper
import com.kaelesty.weatherapp.data.remote.WeatherApiService
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.entities.Forecast
import com.kaelesty.weatherapp.domain.entities.Weather
import com.kaelesty.weatherapp.domain.repos.WeatherRepository
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
	private val mapper: WeatherMapper,
	private val apiService: WeatherApiService
): WeatherRepository {

	private val apiKey = "60112b4b7bf94441b67162419240706"

	override suspend fun getCurrentWeather(city: City): Weather {
		val res = apiService.getCurrentWeather(
			key = apiKey,
			cityName = city.name
		)
		try {
			when (res.code()) {
				200 -> return mapper.Weather_ResponseToDomain(
					res.body() ?: throw IOException()
				)
				else -> {
					throw IOException()
				}
			}
		}
		catch (e: IOException) {
			throw IOException("Could not load current weather (HTTP ${res.code()})")
		}
	}

	override suspend fun getForecast(city: City, days: Int): Forecast {
		val res = apiService.getForecast(
			key = apiKey,
			cityName = city.name,
			days = days
		)
		try {
			when (res.code()) {
				200 -> return mapper.Forecast_ResponseToDomain(
					res.body() ?: throw IOException()
				)
				else -> {
					throw IOException()
				}
			}
		}
		catch (e: IOException) {
			throw IOException("Could not load forecast (HTTP ${res.code()})")
		}
	}
}