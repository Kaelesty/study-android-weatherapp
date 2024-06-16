package com.kaelesty.weatherapp.presentation.search

import com.kaelesty.weatherapp.domain.entities.City
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model: StateFlow<SearchStore.State>

    fun setQuery(newQuery: String)

    fun selectCity(city: City)
}