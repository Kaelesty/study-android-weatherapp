package com.kaelesty.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaelesty.weatherapp.application.ModifiedApplication
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.usecases.SearchCityUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

	@Inject lateinit var searchUseCase: SearchCityUseCase

	val component by lazy {
		(application as ModifiedApplication).applicationComponent
	}

	private val stateFlow: MutableStateFlow<List<City>> = MutableStateFlow(listOf())
	private val scope = CoroutineScope(Dispatchers.IO)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		component.inject(this@MainActivity)

		scope.launch {
			stateFlow.emit(
				searchUseCase("Lon")
			)
		}

		setContent {
			val state by stateFlow.collectAsState()

			Column {
				state.forEach {
					Text(text = "[${it.id}] ${it.name}, ${it.country}")
					Spacer(modifier = Modifier.height(4.dp))
				}
			}
		}
	}
}