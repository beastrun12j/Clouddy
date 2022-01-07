package com.beastrun12j.clouddy.ui.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beastrun12j.clouddy.data.models.HourlyWeatherList
import com.beastrun12j.clouddy.repository.WeatherRepository
import com.beastrun12j.clouddy.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.beastrun12j.clouddy.utils.Constants.LATITUDE
import com.beastrun12j.clouddy.utils.Constants.LONGITUDE

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var hourlyWeatherList = mutableStateOf<List<HourlyWeatherList>>(listOf())
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    var currentDate = mutableStateOf("")
    var currentWeatherType = mutableStateOf("")
    var currentTemp = mutableStateOf("")
    var currentImgUrl = mutableStateOf("")
    var currentHumidity = mutableStateOf("")
    var currentUV = mutableStateOf("")

    init {
        loadCurrentWeatherData()
        loadHourlyWeatherData()
    }

    fun loadHourlyWeatherData() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getWeatherInfo(LATITUDE, LONGITUDE)) {
                is Resource.Success -> {
                    val hourlyEntry = result.data!!.hourly.mapIndexed { _, entry ->
                        val temp = getTemperatureInCelsius(entry.temp)
                        val imgUrl = entry.weather[0].icon
                        val time = getFormattedTime(entry.dt)
                        val highTemp = getTemperatureInCelsiusInteger(entry.feels_like + 1)
                        val lowTemp = getTemperatureInCelsiusInteger(entry.feels_like)
                        HourlyWeatherList(temp, imgUrl, time, highTemp, lowTemp)
                    }
                    hourlyWeatherList.value += hourlyEntry
                    hourlyWeatherList.value = hourlyWeatherList.value.dropLast(42).subList(1, 6)
                    loadError.value = ""
                    isLoading.value = false
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                else -> {}
            }
        }
    }

    fun loadCurrentWeatherData() {
        viewModelScope.launch {
            isLoading.value = false
            when (val result = repository.getWeatherInfo(LATITUDE, LONGITUDE)) {
                is Resource.Success -> {
                    currentDate.value = getFormattedDate(result.data!!.current.dt)
                    currentWeatherType.value = result.data.current.weather[0].main
                    currentTemp.value = getTemperatureInCelsius(result.data.current.temp)
                    currentImgUrl.value = result.data.current.weather[0].icon
                    currentHumidity.value = getHumidityInPercent(result.data.current.humidity)
                    currentUV.value = getFormattedUVRange(result.data.current.uvi)
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
        }

    }
}