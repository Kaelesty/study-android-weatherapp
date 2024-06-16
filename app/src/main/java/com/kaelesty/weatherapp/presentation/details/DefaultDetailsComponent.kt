package com.kaelesty.weatherapp.presentation.details

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.presentation.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultDetailsComponent @AssistedInject constructor(
	@Assisted componentContext: ComponentContext,
	@Assisted private val onReturn: () -> Unit,
	@Assisted private val city: City,
	private val storeFactory: DetailsStoreFactory,
) : DetailsComponent, ComponentContext by componentContext {

	private val store = instanceKeeper.getStore {
		storeFactory.create(city)
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	override val model: StateFlow<DetailsStore.State> = store.stateFlow

	init {
		componentScope("DefaultDetailsComponent").launch {
			store.labels.collect {
				when (it) {
					is DetailsStore.Label.GoBack -> {
						Log.d("DDC", "go back ${this@DefaultDetailsComponent}")
						onReturn()
					}
				}
			}
		}
	}

	override fun addToFavorites() {
		store.accept(DetailsStore.Intent.AddToFavorites)
	}

	override fun delFromFavorites() {
		store.accept(DetailsStore.Intent.DelFromFavorites)
	}

	override fun goBack() {
		store.accept(DetailsStore.Intent.GoBack)
	}

	@AssistedFactory
	interface Factory {
		fun create(
			@Assisted onReturn: () -> Unit,
			@Assisted city: City,
			@Assisted componentContext: ComponentContext
		): DefaultDetailsComponent
	}
}