package com.kaelesty.weatherapp.presentation.favorites

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.usecases.GetFavoriteCitiesUseCase
import com.kaelesty.weatherapp.presentation.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultFavoritesComponent @Inject constructor(
	private val componentContext: ComponentContext,
	private val storeFactory: FavoritesStoreFactory,
	private val onNavigateToCityScreen: (City) -> Unit,
	private val onNavigateToSearchScreen: (FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected) -> Unit

) : FavoritesComponent, ComponentContext by componentContext {

	private val store = instanceKeeper.getStore {
		storeFactory.create()
	}.apply {
		componentScope("DefaultFavoritesComponent").launch {
			labels.collect {
				when (it) {
					is FavoritesStore.Label.NavigateToCityScreen -> {
						onNavigateToCityScreen(it.city)
					}
					is FavoritesStore.Label.NavigateToSearchScreen -> {
						onNavigateToSearchScreen(it.onCitySelected)
					}
				}
			}
		}
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	override val model: StateFlow<FavoritesStore.State>
		get() = store.stateFlow

	override fun onAddNewFavoriteCity() {
		store.accept(
			FavoritesStore.Intent.AddCity
		)
	}

	override fun onShowCityForecast(city: City) {
		store.accept(
			FavoritesStore.Intent.ShowCityForecast(city)
		)
	}

	override fun onSearchCity() {
		store.accept(
			FavoritesStore.Intent.SearchCity
		)
	}

	override fun onLoadCityWeather(city: City) {
		store.accept(
			FavoritesStore.Intent.LoadCityWeather(city)
		)
	}
}