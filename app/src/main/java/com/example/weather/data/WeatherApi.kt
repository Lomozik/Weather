package com.example.weather.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("key") apiKey: String
    ): ForecastResponse

    companion object {
        private val apiInstance: WeatherApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
        fun getInstance(): WeatherApi = apiInstance
    }
}
