package com.kaelesty.weatherapp.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.kaelesty.weatherapp.presentation.favorites.FavoritesComponent

interface RootComponent {

	val stack: Value<ChildStack<*, Child>>

	sealed interface Child {

		class FavoritesList(val component: FavoritesComponent): Child
	}
}