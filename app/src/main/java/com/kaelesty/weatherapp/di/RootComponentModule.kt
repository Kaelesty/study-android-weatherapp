package com.kaelesty.weatherapp.di

import com.kaelesty.weatherapp.presentation.favorites.DefaultFavoritesComponent
import com.kaelesty.weatherapp.presentation.favorites.FavoritesComponent
import com.kaelesty.weatherapp.presentation.root.DefaultRootComponent
import com.kaelesty.weatherapp.presentation.root.RootComponent
import dagger.Binds
import dagger.Module

@Module
interface RootComponentModule {

	@Binds
	fun bindRootComponent(impl: DefaultRootComponent): RootComponent
}