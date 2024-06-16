package com.kaelesty.weatherapp.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailsContent(
	component: DetailsComponent
) {

	val state by component.model.collectAsState()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 32.dp)
			.padding(8.dp)
	) {
		Card {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.background(
						brush = Brush.linearGradient(listOf(Color.Magenta, Color.Yellow))
					)
					.padding(8.dp),
				horizontalAlignment = Alignment.Start
			) {

			}
		}
	}
}