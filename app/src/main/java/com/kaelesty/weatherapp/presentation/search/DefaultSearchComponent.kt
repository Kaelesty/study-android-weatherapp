package com.kaelesty.weatherapp.presentation.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.presentation.componentScope
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSearchComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    private val storeFactory: SearchStoreFactory,
    @Assisted private val onNavigateToFavorites: () -> Unit,
    @Assisted private val onNavigateToDetails: (City) -> Unit,
    @Assisted private val onCitySelected: FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected
) : SearchComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        storeFactory.create(
            onCitySelected
        ).apply {
            componentScope("DefaultSearchComponent").launch {
                labels.collect {
                    when (it) {
                        is SearchStore.Label.NavigateToDetails -> {
                            onNavigateToDetails(it.city)
                        }
                        SearchStore.Label.NavigateToFavorites -> {
                            onNavigateToFavorites()
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchStore.State>
        get() = store.stateFlow

    override fun setQuery(newQuery: String) {
        store.accept(
            SearchStore.Intent.SetQuery(newQuery)
        )
    }

    override fun selectCity(city: City) {
        store.accept(
            SearchStore.Intent.SelectCity(city)
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted componentContext: ComponentContext,
            @Assisted onNavigateToFavorites: () -> Unit,
            @Assisted onNavigateToDetails: (City) -> Unit,
            @Assisted onCitySelected: FavoritesStore.Label.NavigateToSearchScreen.OnCitySelected
        ): DefaultSearchComponent
    }
}