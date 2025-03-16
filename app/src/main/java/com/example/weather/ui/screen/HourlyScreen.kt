package com.example.weather.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.weather.R
import com.example.weather.data.HourlyWeather
import com.example.weather.ui.WeatherViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color

@Composable
fun HourlyForecastScreen(viewModel: WeatherViewModel, navController: NavController, dayIndex: Int) {
    val forecast by viewModel.forecast
    val errorText by viewModel.error.collectAsState()

    if (errorText != null) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            // Back Button
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(text = stringResource(R.string.hourly_forecast), style = MaterialTheme.typography.headlineMedium)
            }

            // Show Error Message
            Text(
                text = errorText!!,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        forecast?.let { data ->
            val hourlyForecast: List<HourlyWeather> = data.forecast.forecastday
                .getOrNull(dayIndex)?.hour.orEmpty()

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = stringResource(R.string.hourly_forecast),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.primary
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(hourlyForecast) { hourData ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.8f
                                )
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = stringResource(R.string.time, hourData.time),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = stringResource(R.string.temp_c, hourData.tempC),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = stringResource(
                                            R.string.condition,
                                            hourData.condition.text
                                        ), style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                Image(
                                    painter = rememberAsyncImagePainter("https:${hourData.condition.icon}"),
                                    contentDescription = "Weather Icon",
                                    modifier = Modifier.size(50.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }
            }
        } ?: Text(text = stringResource(R.string.loading_forecast))
    }
}
