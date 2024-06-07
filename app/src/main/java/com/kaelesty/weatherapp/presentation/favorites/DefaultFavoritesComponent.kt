package com.kaelesty.weatherapp.presentation.favorites

import com.arkivanov.decompose.ComponentContext

class DefaultFavoritesComponent(
	private val componentContext: ComponentContext
) : FavoritesComponent, ComponentContext by componentContext {


}