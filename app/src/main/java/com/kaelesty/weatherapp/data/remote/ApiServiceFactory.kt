package com.kaelesty.weatherapp.data.remote

import com.kaelesty.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceFactory {

	val BASE_URL = "http://api.weatherapi.com/v1/"

	private val apiKey = "60112b4b7bf94441b67162419240706"

	val client = OkHttpClient.Builder()
		.addInterceptor {
			val request = it.request()
			val newUrl = request
				.url()
				.newBuilder()
				.addQueryParameter(
					"key", apiKey
				)
				.build()
			val newRequest = request
				.newBuilder()
				.url(newUrl)
				.build()
			it.proceed(newRequest)
		}
		.build()

	private val retrofit = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.client(client)
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	val cityApiService = retrofit
		.create(CityApiService::class.java)

	val weatherApiService = retrofit
		.create(WeatherApiService::class.java)
}