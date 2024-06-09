package com.kaelesty.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
	entities = [
		CityDbModel::class
	],
	exportSchema = false,
	version = 1
)
abstract class FavoriteCitiesDb: RoomDatabase() {

	abstract fun cityDao(): CityDao


}