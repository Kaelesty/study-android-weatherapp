package com.kaelesty.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun addCity(city: CityDbModel)

	@Query("DELETE FROM favorite_cities WHERE id = :cityId")
	suspend fun delCity(cityId: Int)

	@Query("SELECT * FROM favorite_cities")
	fun getFavoriteCities(): Flow<List<CityDbModel>>

	@Query("SELECT EXISTS (SELECT * FROM favorite_cities WHERE id = :cityId LIMIT 1)")
	fun getIsFavorite(cityId: Int): Flow<Boolean>
}