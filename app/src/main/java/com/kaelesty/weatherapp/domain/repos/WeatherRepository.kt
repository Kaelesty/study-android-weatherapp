package com.kaelesty.weatherapp.domain.repos

import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.entities.Forecast
import com.kaelesty.weatherapp.domain.entities.Weather

interface WeatherRepository {

	suspend fun getCurrentWeather(city: City): Weather

	suspend fun getForecast(city: City, days: Int): Forecast
}