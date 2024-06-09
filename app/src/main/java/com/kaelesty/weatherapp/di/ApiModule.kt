package com.kaelesty.weatherapp.di

import com.kaelesty.weatherapp.data.remote.ApiServiceFactory
import com.kaelesty.weatherapp.data.remote.CityApiService
import com.kaelesty.weatherapp.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

	@Provides
	fun provideCityApiService(): CityApiService  = ApiServiceFactory.cityApiService

	@Provides
	fun provideWeatherApiService(): WeatherApiService = ApiServiceFactory.weatherApiService
}