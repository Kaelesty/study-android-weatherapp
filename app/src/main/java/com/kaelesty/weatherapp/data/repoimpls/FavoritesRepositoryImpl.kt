package com.kaelesty.weatherapp.data.repoimpls

import com.kaelesty.weatherapp.data.local.CityDao
import com.kaelesty.weatherapp.data.mappers.CityMapper
import com.kaelesty.weatherapp.data.remote.CityApiService
import com.kaelesty.weatherapp.domain.entities.City
import com.kaelesty.weatherapp.domain.repos.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
	private val mapper: CityMapper,
	private val apiService: CityApiService,
	//private val dao: CityDao
): FavoritesRepository {

	override fun getFavoriteCities(): Flow<List<City>> {
		TODO("Not yet implemented")
	}

	override fun getIsFavorite(city: City): Flow<Boolean> {
		TODO("Not yet implemented")
	}

	override suspend fun addToFavorites(city: City) {
//		dao.addCity(
//			mapper.City_DomainToDbModel(city)
//		)
	}

	override suspend fun removeFromFavorites(cityId: Int) {
//		dao.delCity(
//			cityId
//		)
	}

	override suspend fun searchCity(query: String): List<City> {
		val res = apiService.search(
			cityName = query
		)
		try {
			when (res.code()) {
				200 -> {
					return res.body()?.map {
						mapper.City_ResponseToDomain(it)
					} ?: throw IOException()
				}
				else -> {
					throw IOException()
				}
			}
		}
		catch (e: IOException) {
			throw IOException("Could not search (HTTP ${res.code()})")
		}
	}
}