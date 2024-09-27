### Weather Application

This application was made in the scope of test for PettersonApps.

Chosen architecure: 
- MVVM (MVI) pattern;
- Single Activity;
- View <-> ViewModel <-> Repository <-> Data source.

Tech stack:
- Kotlin Coroutines;
- Room as local data source;
- Retrofit as remote data source;
- Koin for DI;
- Coil for image loading;
- LocationManager & FusedLocationProvider for location management;
- PlaceApi for suggestions;
- WeatherApi as core api.

The application handles screen orientation changes and can work from API 5.0.
