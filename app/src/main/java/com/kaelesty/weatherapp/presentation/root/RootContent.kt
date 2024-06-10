package com.kaelesty.weatherapp.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.kaelesty.weatherapp.presentation.favorites.FavoritesComponent
import com.kaelesty.weatherapp.presentation.favorites.composables.FavoritesContent
import com.kaelesty.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun RootContent(
	component: RootComponent
) {
	WeatherAppTheme {
		Children(
			stack = component.stack,
			modifier = Modifier.fillMaxSize()
		) {
			when (val instance = it.instance) {
				is FavoritesComponent -> {
					FavoritesContent(component = instance)
				}

				is RootComponent.Child.FavoritesList -> FavoritesContent(instance.component)
			}
		}
	}
}