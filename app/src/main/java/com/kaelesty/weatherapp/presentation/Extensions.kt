package com.kaelesty.weatherapp.presentation

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

fun ComponentContext.componentScope(name: String) = CoroutineScope(
	Dispatchers.Main.immediate + CoroutineExceptionHandler { _, throwable ->
		Log.d(name, throwable.message.toString())
	}
)