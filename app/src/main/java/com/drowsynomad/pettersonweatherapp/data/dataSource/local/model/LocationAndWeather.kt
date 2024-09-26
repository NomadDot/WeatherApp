package com.drowsynomad.pettersonweatherapp.data.dataSource.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.LocationEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.WeatherEntity

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

data class LocationAndWeather(
    @Embedded
    val location: LocationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "place_id",
        entity = WeatherEntity::class
    )
    val weather: WeatherEntity,
)