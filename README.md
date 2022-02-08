# Clouddy - Weather App 

<div align="center">
  <a>
    <img src="https://github.com/beastrun12j/Clouddy/blob/master/Images/icon.png" alt="Logo" width="200">
  </a>

<h4 align="center">Daily Weather Forecast App built with Jetpack Compose</h4>

  <p align="center">
    <a href="https://youtu.be/Jrq3Hc7pEA0">View Demo</a>
    ·
    <a href="https://github.com/beastrun12j/Clouddy/issues">Report Bug</a>
  </p>
</div>


## About the app

- The main goal to make this app was to learn the basics of Jetpack Compose along with other architecture components like Navigation, Dagger-Hilt and Retrofit.
- The app uses [OpenWeatherMap One Call API](https://openweathermap.org/api/one-call-api) to fetch current, hourly, and daily(7 days) weather updates.

<p align="center">
<img src="https://github.com/beastrun12j/Clouddy/blob/master/Images/Untitled%20design.gif" height="420px"/>
</p>

## Architecture

- The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.

<p align="center">
<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" height="420px"/>
</p>

## Design

- Design for the app is inspired from [Furkansin](https://github.com/furkanaskin/Weatherapp).
- Weather icons are taken from [Icons8](https://icons8.com/icon/set/weather/color) under its free-tier subscription.

## About Jetpack Compose

* Jetpack Compose is a modern Android UI toolkit introduced by Google.
* It simplifies the app development process and speeds it up.
* With Jetpack Compose, you can write less code compared to the current view building approach – which also means less potential bugs.
* There is one more great thing about it – it uses Kotlin.
* Compose is not fully bug-free at the moment but has great support and is surely the next big thing in Native Android Development.
* You can learn more about Compose here: [Compose Codelabs by Google](https://developer.android.com/courses/pathways/compose)


## Libraries and tools 🛠

<li><a href="https://developer.android.com/jetpack/compose">Jetpack Compose</a></li>
<li><a href="https://developer.android.com/jetpack/compose/navigation">Navigation</a></li>
<li><a href="https://developer.android.com/topic/libraries/architecture/viewmodel">ViewModel</a></li>
<li><a href="https://developer.android.com/training/dependency-injection/hilt-android">Dagger-Hilt</a></li>
<li><a href="https://developer.android.com/reference/kotlin/androidx/compose/material/icons/Icons">Material Icons</a></li>
<li><a href="https://developer.android.com/jetpack/compose/state">State Management</a></li>
<li><a href="https://developer.android.com/kotlin/coroutines">Coroutines</a></li>
<li><a href="https://square.github.io/retrofit/">Retrofit</a></li>
<li><a href="https://github.com/square/okhttp">OkHttp</a></li>
<li><a href="https://github.com/JakeWharton/timber">Timber</a></li>
<li><a href="https://github.com/Kotlin/kotlinx-datetime">Kotlinx Datetime</a></li>


## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement" or "feature".
Don't forget to give the project a star!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This application is released under MIT License for fair use (see [License](https://github.com/beastrun12j/Clouddy/blob/master/LICENSE)).
