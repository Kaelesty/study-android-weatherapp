package com.kaelesty.weatherapp.di

import com.kaelesty.weatherapp.data.local.FavoriteCitiesDb
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

	@Provides
	fun provideFavoriteCityDao(db: FavoriteCitiesDb) = db.cityDao()
}