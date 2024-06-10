package com.kaelesty.weatherapp.di

import com.arkivanov.decompose.ComponentContext
import com.kaelesty.weatherapp.presentation.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
	modules = [
		RootComponentModule::class
	]
)
interface ActivitySubcomponent {

	fun inject(activity: MainActivity)

	@Subcomponent.Factory
	interface Factory {

		fun create(
			@BindsInstance componentContext: ComponentContext
		): ActivitySubcomponent
	}

}