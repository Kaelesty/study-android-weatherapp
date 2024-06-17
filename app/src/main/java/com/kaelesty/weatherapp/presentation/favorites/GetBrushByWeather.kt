package com.kaelesty.weatherapp.presentation.favorites

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.decapitalize
import com.kaelesty.weatherapp.domain.entities.Weather
import java.util.Locale

fun getBrushByWeather(
	weather: Weather?
): Brush {
	weather?.let {
		return Brush.linearGradient(
			listOf(
				if (weather.desc.decapitalize(Locale.ROOT).contains("rain")) {
					Color.Blue
				} else Color.Yellow,
				if (weather.temp < 6f) {
					Color.Cyan
				} else Color.Magenta
			)
		)
	}
	return Brush.linearGradient(
		listOf(Color.White, Color.DarkGray)
	)
}