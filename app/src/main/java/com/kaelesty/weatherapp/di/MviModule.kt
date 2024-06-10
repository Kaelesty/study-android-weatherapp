package com.kaelesty.weatherapp.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MviModule {

	@Binds
	@ApplicationScope
	abstract fun bindStoreFactory(impl: LoggingStoreFactory): StoreFactory

	companion object {
		@Provides
		@ApplicationScope
		fun provideStoreFactory() = LoggingStoreFactory(DefaultStoreFactory())
	}
}