package com.kaelesty.weatherapp.application

import android.app.Application
import com.kaelesty.weatherapp.di.DaggerApplicationComponent

class ModifiedApplication: Application() {

	val applicationComponent by lazy {
		DaggerApplicationComponent.factory()
			.create(
				this@ModifiedApplication
			)
	}
}