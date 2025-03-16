package com.example.weather.data

import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class WeatherRepository(private val api: WeatherApi = WeatherApi.getInstance()) {
    suspend fun fetchForecast(lat: Double, lon: Double): Result<ForecastResponse> {
        val formattedLat = String.format(Locale.US, "%.4f", lat)
        val formattedLon = String.format(Locale.US, "%.4f", lon)
        val API_KEY = ""
        return try {
            if(API_KEY == "")
                throw IllegalArgumentException("API Key is missing or invalid.")
            val response = api.getForecast("$formattedLat,$formattedLon", 3, API_KEY)

            Result.success(response)

        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                400 -> "Invalid request. Please check location input."
                403 -> "Access denied. Check your API key permissions."
                404 -> "Location not found."
                else -> "Unexpected error: ${e.message}"
            }
            Result.failure(Exception(errorMessage))
        } catch (e: IOException) {
            Result.failure(Exception("Network error. Check your connection."))
        } catch (e: IllegalArgumentException)
        {
            Result.failure(Exception(e.message))
        }
        catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }
}
