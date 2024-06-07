package com.kaelesty.weatherapp.data.mappers

import com.kaelesty.weatherapp.data.remote.dtos.CurrentWeatherResponse
import com.kaelesty.weatherapp.data.remote.dtos.ForecastDay
import com.kaelesty.weatherapp.data.remote.dtos.ForecastResponse
import com.kaelesty.weatherapp.domain.entities.Forecast
import com.kaelesty.weatherapp.domain.entities.Weather
import java.util.Calendar
import java.util.GregorianCalendar
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

	fun Weather_ResponseToDomain(response: CurrentWeatherResponse) =
		Weather(
			temp = response.current.temp,
			iconUrl = response.current.condition.iconUrl,
			desc = response.current.condition.text,
			date = weatherLastUpdatedToCalendar(response.current.date)
		)

	fun Forecast_ResponseToDomain(response: ForecastResponse) =
		Forecast(
			upcoming = response
				.forecast
				.list
				.map { forecastDayToWeather(it) }
		)

	private fun weatherLastUpdatedToCalendar(date: String): Calendar {
		with(
			date
				.split(" ")
				[0]
				.split("-")
				.map { it.toInt() }
		) {
			return GregorianCalendar(this[0], this[1], this[2])
		}
	}

	private fun forecastDayToWeather(day: ForecastDay) =
		Weather(
			temp = day.day.temp,
			iconUrl = day.day.condition.iconUrl,
			desc = day.day.condition.text,
			date = weatherLastUpdatedToCalendar(day.date)
		)
}