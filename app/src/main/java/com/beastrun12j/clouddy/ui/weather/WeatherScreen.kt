package com.beastrun12j.clouddy.ui.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beastrun12j.clouddy.R
import com.beastrun12j.clouddy.ui.theme.Pink
import com.beastrun12j.clouddy.ui.theme.Purple
import com.beastrun12j.clouddy.ui.theme.Vazir
import com.beastrun12j.clouddy.utils.getImageFromUrl
import com.beastrun12j.clouddy.utils.getUVIndexColor
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val scrollStateScreen = rememberScrollState()
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }
    if (!isLoading && loadError.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .verticalScroll(scrollStateScreen)
        ) {
            TodaysDateBox()
            CurrentWeatherBox()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.later_today_text),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                )
            }
            HourlyWeatherList()
        }
    } else if (loadError.isNotEmpty()) {
        RetrySection(error = loadError) {
            viewModel.loadCurrentWeatherData()
            viewModel.loadHourlyWeatherData()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.Cyan)
        }
    }
}

@Composable
fun TodaysDateBox(viewModel: WeatherViewModel = hiltViewModel()) {
    val currentDate by remember { viewModel.currentDate }
    val isLoading by remember { viewModel.isLoading }
    val loadingError by remember { viewModel.loadError }
    if (!isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                shape = RoundedCornerShape(32.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = currentDate,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(8.dp),
                        fontFamily = Vazir,
                        fontSize = 19.sp
                    )
                }
            }
        }
    } else {
        Timber.e(loadingError)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentWeatherBox(viewModel: WeatherViewModel = hiltViewModel()) {

    val currentTemp by remember { viewModel.currentTemp }
    val currentWeatherType by remember { viewModel.currentWeatherType }
    val currentHumidity by remember { viewModel.currentHumidity }
    val currentUV by remember { viewModel.currentUV }
    val currentImgUrl by remember { viewModel.currentImgUrl }
    val isLoading by remember { viewModel.isLoading }
    val uvIndexColor = getUVIndexColor(currentUV)

    if (!isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp, 16.dp, 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Purple)
                    .padding(4.dp)
            ) {
                Column {
                    Text(
                        text = currentTemp,
                        color = Color.White,
                        fontSize = 72.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        fontFamily = Vazir
                    )
                    Text(
                        text = currentWeatherType,
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(0.dp, 0.dp, 24.dp, 8.dp))
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_humidity),
                                    contentDescription = stringResource(R.string.description)
                                )
                                Text(
                                    text = "Humidity $currentHumidity",
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(6.dp, 12.dp),
                                    fontSize = 18.sp
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_wind),
                                    contentDescription = stringResource(R.string.description)
                                )
                                Text(
                                    text = "UV $currentUV",
                                    color = uvIndexColor,
                                    modifier = Modifier
                                        .padding(6.dp, 12.dp),
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Image(
                            painter = painterResource(id = getImageFromUrl(currentImgUrl)),
                            contentDescription = stringResource(R.string.description),
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyWeatherList(viewModel: WeatherViewModel = hiltViewModel()) {
    val hourlyWeatherList by remember { viewModel.hourlyWeatherList }
    val isLoading by remember { viewModel.isLoading }

    LazyRow {
        items(hourlyWeatherList) {
            if (!isLoading) {
                HourlyWeatherBox(
                    time = it.time,
                    imgUrl = it.imgUrl,
                    temp = it.temp,
                    tempHigh = it.highTemp,
                    tempLow = it.lowTemp
                )
            }
        }
    }
}

@Composable
fun HourlyWeatherBox(
    time: String,
    imgUrl: String,
    temp: String,
    tempHigh: String,
    tempLow: String
) {
    Box(modifier = Modifier.padding(8.dp, 4.dp)) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Pink)
                .padding(4.dp)
                .size(width = 200.dp, height = 270.dp)
        ) {
            Column(
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = time,
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(0.dp, 8.dp, 0.dp, 8.dp))
                )

                Image(
                    painter = painterResource(id = getImageFromUrl(imgUrl)),
                    contentDescription = stringResource(R.string.description),
                    modifier = Modifier.size(96.dp)
                )

                Text(
                    text = temp,
                    color = Color.White,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(0.dp, 0.dp, 0.dp, 8.dp))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier.padding(24.dp, 0.dp)
                    ) {
                        Icon(
                            Icons.Outlined.ExpandMore,
                            contentDescription = stringResource(R.string.description),
                            modifier = Modifier.size(40.dp),
                            Color.White
                        )
                        Text(
                            text = tempLow,
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(PaddingValues(0.dp, 0.dp, 0.dp, 8.dp))
                        )
                    }
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier.padding(24.dp, 0.dp)
                    ) {
                        Icon(
                            Icons.Filled.ExpandLess,
                            contentDescription = stringResource(R.string.description),
                            modifier = Modifier.size(40.dp),
                            Color.White
                        )
                        Text(
                            text = tempHigh,
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(PaddingValues(0.dp, 0.dp, 0.dp, 8.dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.retry_text), color = Color.White)
        }
    }
}
