package com.kaelesty.weatherapp.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.kaelesty.weatherapp.presentation.details.DetailsComponent
import com.kaelesty.weatherapp.presentation.details.DetailsContent
import com.kaelesty.weatherapp.presentation.favorites.FavoritesComponent
import com.kaelesty.weatherapp.presentation.favorites.composables.FavoritesContent
import com.kaelesty.weatherapp.presentation.search.SearchContent
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
				is RootComponent.Child.FavoritesList -> FavoritesContent(component = instance.component)
				is RootComponent.Child.Details -> DetailsContent(component = instance.component)
				is RootComponent.Child.Search -> SearchContent(component = instance.component)
			}
		}
	}
}