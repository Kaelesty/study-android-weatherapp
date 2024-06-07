package com.kaelesty.weatherapp.data.remote.dtos

import com.google.gson.annotations.SerializedName


data class Condition(
	@SerializedName("text") val text: String,
	@SerializedName("icon") val iconUrl: String,
)