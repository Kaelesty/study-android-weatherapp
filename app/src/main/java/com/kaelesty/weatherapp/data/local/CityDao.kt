package com.kaelesty.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addCity(city: CityDbModel)

	@Query("DELETE FROM favorite_cities WHERE id = :cityId")
	fun delCity(cityId: Int)


}