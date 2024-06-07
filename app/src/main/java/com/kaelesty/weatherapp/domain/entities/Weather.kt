package com.kaelesty.weatherapp.domain.entities

import java.util.Calendar

data class Weather(
	val temp: Float,
	val desc: String,
	val iconUrl: String,
	val date: Calendar
)
