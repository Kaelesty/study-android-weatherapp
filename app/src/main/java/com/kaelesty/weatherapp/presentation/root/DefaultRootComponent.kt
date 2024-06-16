package com.kaelesty.weatherapp.presentation.root

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.presentation.details.DefaultDetailsComponent
import com.kaelesty.weatherapp.presentation.favorites.DefaultFavoritesComponent
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore
import com.kaelesty.weatherapp.presentation.search.DefaultSearchComponent
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultRootComponent @Inject constructor(
	componentContext: ComponentContext,
	private val favoritesComponentFactory: DefaultFavoritesComponent.Factory,
	private val detailsComponentFactory: DefaultDetailsComponent.Factory,
	private val searchComponentFactory: DefaultSearchComponent.Factory,
) : ComponentContext by componentContext, RootComponent {

	private val navigation = StackNavigation<Config>()

	override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
		source = navigation,
		initialConfiguration = Config.FavoritesList,
		handleBackButton = true,
		serializer = Config.serializer(),
		childFactory = ::child
	)

	private fun child(
		config: Config,
		componentContext: ComponentContext,
	): RootComponent.Child {
		return when (config) {

			is Config.FavoritesList -> {
				RootComponent.Child.FavoritesList(
					component = favoritesComponentFactory
						.create(
							componentContext,
							onNavigateToCityScreen = {
								Log.d("RootComponent", "onNavigateToCityScreen")
								navigation.push(
									Config.CityForecast(it)
								)
							},
							onNavigateToSearchScreen = {
								navigation.push(
									Config.Search(it)
								)
							}
						)
				)
			}

			is Config.CityForecast -> {
				RootComponent.Child.Details(
					component = detailsComponentFactory
						.create(
							city = config.city,
							onReturn = {
								navigation.pop()
							},
							componentContext = componentContext
						)
				)
			}
			is Config.Search -> {
				RootComponent.Child.Search(
					component = searchComponentFactory
						.create(
							componentContext,
							onNavigateToDetails = {
								navigation.pop()
								navigation.push(
									Config.CityForecast(it)
								)
							},
							onNavigateToFavorites = {
								navigation.pop()
							},
							onCitySelected = config.onCitySelected
						)
				)
			}
		}
	}

	@Serializable
	private sealed interface Config {

		@Serializable
		object FavoritesList : Config

		@Serializable
		class Search(val onCitySelected: FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected) :
			Config

		@Serializable
		class CityForecast(val city: City) : Config
	}
}