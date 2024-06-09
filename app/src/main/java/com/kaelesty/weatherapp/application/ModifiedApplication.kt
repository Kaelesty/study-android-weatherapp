package com.kaelesty.weatherapp.application

import android.app.Application
import androidx.room.Room
import com.kaelesty.weatherapp.data.local.FavoriteCitiesDb
import com.kaelesty.weatherapp.di.DaggerApplicationComponent

class ModifiedApplication: Application() {

	val applicationComponent by lazy {
		DaggerApplicationComponent.factory()
			.create(
				this@ModifiedApplication,
				favoriteCitiesDb = Room.databaseBuilder(
					this@ModifiedApplication,
					FavoriteCitiesDb::class.java,
					"bd"
				)
					.build()
			)
	}
}