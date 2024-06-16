package com.kaelesty.weatherapp.presentation.search

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.usecases.AddToFavoritesUseCase
import com.kaelesty.weatherapp.domain.usecases.SearchCityUseCase
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore
import com.kaelesty.weatherapp.presentation.search.SearchStore.Intent
import com.kaelesty.weatherapp.presentation.search.SearchStore.Label
import com.kaelesty.weatherapp.presentation.search.SearchStore.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data class SetQuery(val query: String): Intent

        data class SelectCity(val city: City): Intent
    }

    data class State(
        val query: String,
        val results: List<City>,
        val isLoading: Boolean
    )

    sealed interface Label {
        object NavigateToFavorites: Label

        class NavigateToDetails(val city: City): Label
    }
}

class SearchStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val searchCityUseCase: SearchCityUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase
) {

    private val scope = CoroutineScope(
        Dispatchers.IO +
                CoroutineExceptionHandler { _, throwable ->
                    Log.d("DetailsStore", "${throwable.message}")
                }
    )

    fun create(onCitySelected: FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected): SearchStore =
        object : SearchStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SearchStore",
            initialState = State(
                "", listOf(), false
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(onCitySelected) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
        class setSearchResults(val results: List<City>): Msg
        object startLoading: Msg
        data class setQuery(val query: String): Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {}
    }

    private inner class ExecutorImpl(
        private val onCitySelected: FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.SelectCity -> {
                    when (onCitySelected)  {
                        is FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected.AddToFavorite -> {
                            scope.launch {
                                addToFavoritesUseCase(intent.city)
                                publish(
                                    Label.NavigateToFavorites
                                )
                            }
                        }
                        is FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected.ShowForecast -> {
                            publish(
                                Label.NavigateToDetails(intent.city)
                            )
                        }
                    }
                }
                is Intent.SetQuery -> {
                    dispatch(
                        Msg.setQuery(intent.query)
                    )
                    scope.launch {
                        val results = searchCityUseCase(intent.query)
                        dispatch(Msg.setSearchResults(results))
                    }
                }
            }
        }

        override fun executeAction(action: Action) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.setSearchResults -> {
                    copy(
                        results = message.results,
                        isLoading = false
                    )
                }
                is Msg.startLoading -> {
                    copy(
                        isLoading = true
                    )
                }
                is Msg.setQuery -> {
                    copy(query = message.query)
                }
            }
    }
}
