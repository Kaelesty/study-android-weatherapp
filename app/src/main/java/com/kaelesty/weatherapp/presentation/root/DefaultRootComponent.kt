package com.kaelesty.weatherapp.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.kaelesty.weatherapp.presentation.favorites.DefaultFavoritesComponent
import com.kaelesty.weatherapp.presentation.favorites.FavoritesStoreFactory
import kotlinx.serialization.Serializable
import javax.inject.Inject

class DefaultRootComponent @Inject constructor(
	private val componentContext: ComponentContext,
	private val storeFactory: FavoritesStoreFactory
) : ComponentContext by componentContext, RootComponent {

	private val navigation = StackNavigation<Config>()

	override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
		source = navigation,
		initialConfiguration = Config.FavoritesList,
		handleBackButton = true,
		serializer = Config.serializer(),
		childFactory = ::child
	)

	private fun child(
		config: Config,
		componentContext: ComponentContext,
	): RootComponent.Child {
		return when (config) {
			is Config.FavoritesList -> {

				RootComponent.Child.FavoritesList(
					component = DefaultFavoritesComponent(
						componentContext,
						onNavigateToCityScreen = {},
						onNavigateToSearchScreen = {},
						storeFactory = storeFactory
					)
				)
			}
		}
	}

	@Serializable
	private sealed interface Config {

		@Serializable
		object FavoritesList : Config
	}
}