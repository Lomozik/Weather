package com.example.weather.data

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("forecast") val forecast: Forecast
)

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("day") val dayDetails: DayDetails,
    @SerializedName("hour") val hour: List<HourlyWeather>
)

data class DayDetails(
    @SerializedName("maxtemp_c") val maxTempC: Double,
    @SerializedName("mintemp_c") val minTempC: Double,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("maxwind_kph") val windSpeed: Double
)

data class HourlyWeather(
    @SerializedName("time") val time: String,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("condition") val condition: Condition
)

data class Condition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String
)
