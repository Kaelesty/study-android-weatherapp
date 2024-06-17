package com.kaelesty.weatherapp.presentation.details

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kaelesty.weatherapp.R
import com.kaelesty.weatherapp.presentation.favorites.getBrushByWeather

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsContent(
	component: DetailsComponent
) {

	val state by component.model.collectAsState()

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp)
			.padding(8.dp)
	) {
		Spacer(modifier = Modifier.height(150.dp))
		Card(
			Modifier
				.height(250.dp)
		) {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.background(
						brush = getBrushByWeather(state.weather)
					)
					.padding(8.dp, top = 32.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					text = state.city.name,
					fontWeight = FontWeight.Bold,
					fontSize = 30.sp
				)
				Text(
					text = state.city.country,
					fontSize = 24.sp,
				)
				Spacer(modifier = Modifier.height(30.dp))
				if (state.weather == null) {
					CircularProgressIndicator(
						modifier = Modifier
							.size(60.dp)
					)
				}
				else {
					Row(
						modifier = Modifier
							.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(
							text = "${state.weather?.temp}°C",
							fontSize = 75.sp
						)
						Spacer(modifier = Modifier.width(8.dp))
						Column {
							GlideImage(
								model = "http://${state.weather?.iconUrl}",
								contentDescription = null,
								Modifier.size(80.dp),
								loading = placeholder(R.drawable.ic_launcher_foreground),
							)
							Text(
								text = "${state.weather?.desc}",
								fontWeight = FontWeight.Thin,
								fontSize = 24.sp,
							)
						}
					}
				}
			}
		}
		Spacer(modifier = Modifier.height(50.dp))
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.fillMaxWidth()
		) {
			state.forecast?.let {
				it.upcoming.forEach { weather ->
					Card(
						modifier = Modifier
							.padding(4.dp)
					) {
						Column(
							modifier = Modifier
								.width(120.dp)
								.height(160.dp)
								.background(
									brush = getBrushByWeather(weather)
								),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							GlideImage(
								model = "http://${weather.iconUrl}",
								contentDescription = null,
								Modifier.size(90.dp),
								loading = placeholder(R.drawable.ic_launcher_foreground),
							)
							Box(
								modifier = Modifier
									.fillMaxWidth()
									.padding(horizontal = 4.dp)
									.height(40.dp)
							) {
								Text(
									text = "${weather.desc}",
									fontSize = 16.sp,
								)
							}
							Text(
								text = "${weather.temp}°C",
								fontSize = 24.sp
							)
							Spacer(modifier = Modifier.height(4.dp))
						}
					}
				}
			}
		}
	}
}