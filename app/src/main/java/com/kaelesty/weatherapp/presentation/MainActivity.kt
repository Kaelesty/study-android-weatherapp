package com.kaelesty.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaelesty.weatherapp.application.ModifiedApplication
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.usecases.AddToFavoritesUseCase
import com.kaelesty.weatherapp.domain.usecases.GetFavoriteCitiesUseCase
import com.kaelesty.weatherapp.domain.usecases.SearchCityUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

	@Inject lateinit var getFavoritesUseCase: GetFavoriteCitiesUseCase
	@Inject lateinit var addToFavoriteCitiesUseCase: AddToFavoritesUseCase

	val component by lazy {
		(application as ModifiedApplication).applicationComponent
	}

	private val scope = CoroutineScope(Dispatchers.IO)
	private lateinit var stateFlow: StateFlow<List<City>>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		component.inject(this@MainActivity)

		stateFlow = getFavoritesUseCase()
			.stateIn(
				scope = scope,
				started = SharingStarted.Eagerly,
				initialValue = listOf()
			)

		setContent {
			val state by stateFlow.collectAsState()
			var counter by remember {
				mutableIntStateOf(1)
			}
			Button(onClick = {
				scope.launch {
					addToFavoriteCitiesUseCase(
						City(
							id = counter,
							name = "Vault 1$counter",
							country = "VaultTec"
						)
					)
					counter += 1
				}
			}) {
				Text("Add")
			}

			Column {
				state.forEach {
					Text(text = "[${it.id}] ${it.name}, ${it.country}")
					Spacer(modifier = Modifier.height(4.dp))
				}
			}
		}
	}
}