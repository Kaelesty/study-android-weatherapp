package com.kaelesty.weatherapp.data.remote

import com.kaelesty.weatherapp.data.remote.dtos.CurrentWeatherResponse
import com.kaelesty.weatherapp.data.remote.dtos.ForecastResponse
import com.kaelesty.weatherapp.data.remote.dtos.SearchCityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CityApiService {

	@GET("search.json")
	suspend fun search(
		@Query("q") cityName: String,
	): Response<List<SearchCityResponse>>
}