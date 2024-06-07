package com.kaelesty.weatherapp.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
	@SerializedName("forecast") val forecast: ForecastDays
)

data class ForecastDays(
	@SerializedName("forecastday") val list: List<ForecastDay>
)

data class ForecastDay(
	@SerializedName("date") val date: String,
	@SerializedName("day") val day: Day
)

data class Day(
	@SerializedName("avgtemp_c") val temp: Float,
	@SerializedName("condition") val condition: Condition
)