package com.kaelesty.weatherapp.data.mappers

import com.kaelesty.weatherapp.data.local.CityDbModel
import com.kaelesty.weatherapp.data.remote.dtos.SearchCityResponse
import com.kaelesty.weatherapp.domain.entities.City
import javax.inject.Inject

class CityMapper @Inject constructor() {

	fun City_ResponseToDomain(response: SearchCityResponse) =
		City(
			id = response.id,
			country = response.country,
			name = response.name,
		)

	fun City_DomainToDbModel(domain: City) =
		CityDbModel(
			id = domain.id,
			country = domain.country,
			name = domain.name
		)

	fun City_DbModelToDomain(dbModel: CityDbModel) =
		City(
			id = dbModel.id,
			country = dbModel.country,
			name = dbModel.name
		)
}