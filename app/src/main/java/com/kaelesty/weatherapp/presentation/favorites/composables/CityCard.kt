package com.kaelesty.weatherapp.presentation.favorites.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kaelesty.weatherapp.R
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.entities.Weather

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CityCard(
	city: City,
	weather: Weather?,
	modifier: Modifier = Modifier
) {
	Card(
		modifier = modifier
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(
					brush = Brush.linearGradient(listOf(Color.Magenta, Color.Yellow))
				)
				.padding(8.dp),
			horizontalAlignment = Alignment.Start
		) {
			if (weather != null) {
				Box(modifier = Modifier.align(Alignment.End)) {
					GlideImage(
						model = "http://${weather.iconUrl}",
						contentDescription = stringResource(R.string.weather_icon),
						modifier = Modifier
							.size(100.dp),
						loading = placeholder(R.drawable.ic_launcher_foreground),
					)
				}
				Text(
					text = "${weather.temp}°C",
					fontWeight = FontWeight.ExtraBold,
					fontSize = 45.sp
				)
			}
			else {
				CircularProgressIndicator(
					modifier = Modifier.size(60.dp)
				)
			}
			Text(
				text = "${city.name}",
				fontSize = 24.sp,
				fontWeight = FontWeight.Thin
			)
		}
	}
}