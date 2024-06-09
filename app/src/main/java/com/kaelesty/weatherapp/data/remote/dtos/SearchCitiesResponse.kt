package com.kaelesty.weatherapp.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class SearchCityResponse(
	@SerializedName("id") val id: Int,
	@SerializedName("name") val name: String,
	@SerializedName("country") val country: String,
)