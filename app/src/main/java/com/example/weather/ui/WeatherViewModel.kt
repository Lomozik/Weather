package com.example.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherRepository
import kotlinx.coroutines.launch

import com.example.weather.data.ForecastResponse
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _forecast = mutableStateOf<ForecastResponse?>(null)
    val forecast: State<ForecastResponse?> get() = _forecast

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchForecast(lat: Double?, lon: Double?) {
        if (lat == null || lon == null) {
            _error.value = "⚠️ Location unavailable"
            return
        }
        if (_forecast.value != null) return

        viewModelScope.launch {
            repository.fetchForecast(lat, lon)
                .onSuccess { response ->
                    _forecast.value = response
                    _error.value = null
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
        }
    }
}
