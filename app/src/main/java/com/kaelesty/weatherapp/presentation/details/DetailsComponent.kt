package com.kaelesty.weatherapp.presentation.details

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

	val model: StateFlow<DetailsStore.State>

	fun addToFavorites()

	fun delFromFavorites()

	fun goBack()
}