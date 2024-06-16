package com.kaelesty.weatherapp.presentation.favorites

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.entities.Weather
import com.kaelesty.weatherapp.domain.usecases.AddToFavoritesUseCase
import com.kaelesty.weatherapp.domain.usecases.GetCurrentWeatherUseCase
import com.kaelesty.weatherapp.domain.usecases.GetFavoriteCitiesUseCase
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore.Intent
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore.Label
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavoritesStore : Store<Intent, State, Label> {

    sealed interface Intent {
        object AddCity: Intent
        class ShowCityForecast(val city: City): Intent
        class SearchCity(val onCitySelected: Label.NavigateToSearchScreen.OnCitySelected): Intent
        class LoadCityWeather(val city: City): Intent
    }

    data class State(
        val cities: List<CityWeather>
    ) {
        data class CityWeather(
            val city: City,
            val weather: Weather?
        )
    }

    sealed interface Label {

        class NavigateToSearchScreen(val onCitySelected: OnCitySelected): Label {
            sealed interface OnCitySelected {
                object AddToFavorite: OnCitySelected
                object ShowForecast: OnCitySelected
            }
        }

        class NavigateToCityScreen(val city: City): Label
    }
}

class FavoritesStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getFavoritesUseCase: GetFavoriteCitiesUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
) {

    private val scope = CoroutineScope(
        Dispatchers.IO +
        CoroutineExceptionHandler { _, throwable ->
            Log.d("FavoritesStore", "${throwable.message}")
        }
    )

    fun create(): FavoritesStore =
        object : FavoritesStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavoritesStore",
            initialState = State(listOf()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        class FavoritesListLoaded(val cities: List<City>): Action
    }

    private sealed interface Msg {

        class ChangeFavoritesList(val newFavorites: List<City>): Msg

        class SetCityWeather(
            val city: City,
            val weather: Weather
        ): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getFavoritesUseCase().collect {
                    dispatch(
                        Action.FavoritesListLoaded(it)
                    )
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.AddCity -> {
                    publish(
                        Label.NavigateToSearchScreen(
                            Label.NavigateToSearchScreen.OnCitySelected.AddToFavorite
                        )
                    )
                }
                is Intent.ShowCityForecast -> {
                    publish(
                        Label.NavigateToCityScreen(
                            intent.city
                        )
                    )
                }
                is Intent.SearchCity -> {
                    publish(
                        Label.NavigateToSearchScreen(
                            intent.onCitySelected
                        )
                    )
                }
                is Intent.LoadCityWeather -> {
                    scope.launch {
                        with(intent) {
                            dispatch(
                                Msg.SetCityWeather(
                                    city = city,
                                    weather = getCurrentWeatherUseCase(city)
                                )
                            )
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                is Action.FavoritesListLoaded -> {
                    dispatch(Msg.ChangeFavoritesList(action.cities))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.ChangeFavoritesList -> {
                    copy(
                        cities = message.newFavorites.map {
                            State.CityWeather(it, null)
                        }
                    )
                }
                is Msg.SetCityWeather -> {
                    copy(
                        cities = this.cities.map {
                            if (it.city.id == message.city.id) {
                                it.copy(
                                    weather = message.weather
                                )
                            }
                            else {
                                it
                            }
                        }
                    )
                }
            }
    }
}
