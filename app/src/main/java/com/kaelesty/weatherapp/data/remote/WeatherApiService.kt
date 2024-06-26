package com.kaelesty.weatherapp.data.remote

import com.kaelesty.weatherapp.data.remote.dtos.CurrentWeatherResponse
import com.kaelesty.weatherapp.data.remote.dtos.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

	@GET("current.json")
	suspend fun getCurrentWeather(
		@Query("q") cityName: String,
	): Response<CurrentWeatherResponse>

	@GET("forecast.json")
	suspend fun getForecast(
		@Query("q") cityName: String,
		@Query("days") days: Int
	): Response<ForecastResponse>
}