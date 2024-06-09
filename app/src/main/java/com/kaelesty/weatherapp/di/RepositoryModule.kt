package com.kaelesty.weatherapp.di

import com.kaelesty.weatherapp.data.repoimpls.FavoritesRepositoryImpl
import com.kaelesty.weatherapp.data.repoimpls.WeatherRepositoryImpl
import com.kaelesty.weatherapp.domain.repos.FavoritesRepository
import com.kaelesty.weatherapp.domain.repos.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RepositoryModule {

	@Binds
	abstract fun provideFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

	@Binds
	abstract fun provideWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository
}