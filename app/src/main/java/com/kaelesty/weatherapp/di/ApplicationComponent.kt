package com.kaelesty.weatherapp.di

import android.app.Application
import com.kaelesty.weatherapp.data.local.FavoriteCitiesDb
import com.kaelesty.weatherapp.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(
	modules = [
		ApiModule::class,
		RepositoryModule::class,
		DaoModule::class,
		MviModule::class,
	]
)
@ApplicationScope
interface ApplicationComponent {

	fun inject(activity: MainActivity)

	@Component.Factory
	interface ApplicationComponentFactory {
		fun create(
			@BindsInstance application: Application,
			@BindsInstance favoriteCitiesDb: FavoriteCitiesDb,
		): ApplicationComponent
	}
}