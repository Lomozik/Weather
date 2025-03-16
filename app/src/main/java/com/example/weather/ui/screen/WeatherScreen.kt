package com.example.weather.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.weather.R
import com.example.weather.data.ForecastDay
import com.example.weather.ui.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel, navController: NavController) {
    val forecast by viewModel.forecast
    val errorText by viewModel.error.collectAsState()

    if (errorText != null) {
        Text(
            text = errorText!!,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        forecast?.let { data ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string._3_day_forecast),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    itemsIndexed(data.forecast.forecastday) { index, dayData ->
                        DayForecastItem(dayData) {
                            navController.navigate("hourly_forecast/$index")
                        }
                    }
                }
            }
        } ?: Text(text = stringResource(R.string.loading_forecast))
    }
}

@Composable
fun DayForecastItem(dayData: ForecastDay, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = stringResource(R.string.date, dayData.date), style = MaterialTheme.typography.bodyLarge)
                Text(text = stringResource(R.string.min_c, dayData.dayDetails.minTempC), style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(R.string.max_c, dayData.dayDetails.maxTempC), style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(
                    R.string.condition,
                    dayData.dayDetails.condition.text
                ), style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(R.string.wind_kph, dayData.dayDetails.windSpeed), style = MaterialTheme.typography.bodyMedium)
            }

            Image(
                painter = rememberAsyncImagePainter("https:${dayData.dayDetails.condition.icon}"),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

