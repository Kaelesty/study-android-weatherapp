package com.kaelesty.weatherapp.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
	@SerializedName("current") val current: CurrentWeather,
)

data class CurrentWeather(
	@SerializedName("temp_c") val temp: Float,
	@SerializedName("last_updated") val date: String,
	@SerializedName("condition") val condition: Condition,
)
