package com.kaelesty.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_cities")
data class CityDbModel(
	@PrimaryKey val id: Int,
	val name: String,
	val country: String
)
