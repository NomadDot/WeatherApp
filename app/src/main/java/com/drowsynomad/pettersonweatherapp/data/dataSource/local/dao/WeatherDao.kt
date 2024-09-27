package com.drowsynomad.pettersonweatherapp.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.LocationEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.WeatherEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.model.LocationAndWeather

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

@Dao
interface WeatherDao {
    @Insert(entity = LocationEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocation(locationEntity: LocationEntity)

    @Insert(entity = WeatherEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weatherEntity: WeatherEntity)

    @Query("SELECT COUNT(*) FROM location WHERE place_name = :place")
    suspend fun isLocationSaved(place: String): Boolean

    @Query("SELECT * from location ORDER BY place_name ASC")
    suspend fun loadSavedLocations(): List<LocationEntity>

    @Query("SELECT * from location WHERE place_name = :place")
    suspend fun loadWeatherForPlace(place: String): LocationAndWeather?

    @Query("DELETE FROM location WHERE place_name = :place")
    suspend fun deleteLocation(place: String)
}