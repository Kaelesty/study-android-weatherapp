package com.kaelesty.weatherapp.presentation.details

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.entities.Forecast
import com.kaelesty.weatherapp.domain.entities.Weather
import com.kaelesty.weatherapp.domain.usecases.AddToFavoritesUseCase
import com.kaelesty.weatherapp.domain.usecases.GetCurrentWeatherUseCase
import com.kaelesty.weatherapp.domain.usecases.GetForecastUseCase
import com.kaelesty.weatherapp.domain.usecases.GetIsFavoriteUseCase
import com.kaelesty.weatherapp.domain.usecases.RemoveFromFavoritesUseCase
import com.kaelesty.weatherapp.presentation.details.DetailsStore.Intent
import com.kaelesty.weatherapp.presentation.details.DetailsStore.Label
import com.kaelesty.weatherapp.presentation.details.DetailsStore.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailsStore : Store<Intent, State, Label> {

    sealed interface Intent {

        object AddToFavorites: Intent

        object DelFromFavorites: Intent

        object GoBack: Intent
    }

    data class State(
		val city: City,
		val weather: Weather?,
		val forecast: Forecast?,
        val isFavorite: Boolean
	)

    sealed interface Label {

        object GoBack: Label
    }
}

class DetailsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val delFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val getIsFavoriteUseCase: GetIsFavoriteUseCase,
) {

    private val scope = CoroutineScope(
        Dispatchers.IO +
                CoroutineExceptionHandler { _, throwable ->
                    Log.d("DetailsStore", "${throwable.message}")
                }
    )

    fun create(city: City): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(
				city = city,
				weather = null,
				forecast = null,
                false
			),
            bootstrapper = BootstrapperImpl(city),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        class SetWeather(val weather: Weather): Action

        class SetForecast(val forecast: Forecast): Action

        class SetIsLiked(val isLiked: Boolean): Action
    }

    private sealed interface Msg {

        class SetWeather(val weather: Weather): Msg

        class SetForecast(val forecast: Forecast): Msg

        class SetIsFavorite(val isLiked: Boolean): Msg
    }

    private inner class BootstrapperImpl(private val city: City) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(
                    Action.SetWeather(
                        getCurrentWeatherUseCase(city)
                    )
                )
            }
            scope.launch {
                dispatch(
                    Action.SetForecast(
                        getForecastUseCase(city)
                    )
                )
            }
            scope.launch {
                getIsFavoriteUseCase(city).collect {
                    dispatch(
                        Action.SetIsLiked(it)
                    )
                }
            }
        }
    }

    private inner class ExecutorImpl() : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                Intent.AddToFavorites -> {
                    scope.launch {
                        addToFavoritesUseCase(state().city)
                    }
                }
                Intent.DelFromFavorites -> {
                    scope.launch {
                        delFromFavoritesUseCase(state().city.id)
                    }
                }
                Intent.GoBack -> {
                    publish(Label.GoBack)
                }
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                is Action.SetForecast -> {
                    dispatch(Msg.SetForecast(action.forecast))
                }
                is Action.SetIsLiked -> {
                    dispatch(Msg.SetIsFavorite(action.isLiked))
                }
                is Action.SetWeather -> {
                    dispatch(Msg.SetWeather(action.weather))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.SetForecast -> {
                    copy(
                        forecast = message.forecast
                    )
                }
                is Msg.SetIsFavorite -> {
                    copy(
                        isFavorite = message.isLiked
                    )
                }
                is Msg.SetWeather -> {
                    copy(
                        weather = message.weather
                    )
                }
            }
    }
}
