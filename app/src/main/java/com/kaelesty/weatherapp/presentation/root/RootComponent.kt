package com.kaelesty.weatherapp.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.kaelesty.weatherapp.presentation.details.DetailsComponent
import com.kaelesty.weatherapp.presentation.favorites.FavoritesComponent
import com.kaelesty.weatherapp.presentation.search.SearchComponent

interface RootComponent {

	val stack: Value<ChildStack<*, Child>>

	sealed interface Child {

		class FavoritesList(val component: FavoritesComponent): Child

		class Details(val component: DetailsComponent): Child

		class Search(val component: SearchComponent): Child
	}
}