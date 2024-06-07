package com.kaelesty.weatherapp.presentation.favorites

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore.Intent
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore.Label
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStore.State

internal interface FavoritesStore : Store<Intent, State, Label> {

    sealed interface Intent {
    }

    data class State(
        val todo: Unit
	)

    sealed interface Label {
    }
}

internal class FavoritesStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): FavoritesStore =
        object : FavoritesStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavoritesStore",
            initialState = State(Unit),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
        }

        override fun executeAction(action: Action) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                else -> {
                    State(Unit)
                }
            }
    }
}
