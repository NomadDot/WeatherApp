package com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

@Entity(
    tableName = "weather",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["place_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @ColumnInfo(name = "place_id")
    val placeId: Int = 0,
    @Embedded(prefix = "temperature_")
    val temperature: Temperature,
    @Embedded(prefix = "weather_")
    val info: Weather,
) {
    data class Temperature(
        @ColumnInfo(name = "current")
        val current: Double = 0.0,
        @ColumnInfo(name = "min")
        val min: Double = 0.0,
        @ColumnInfo(name = "max")
        val max: Double = 0.0,
        @ColumnInfo(name = "feels_like")
        val feelsLike: Double = 0.0
    )

    data class Weather(
        val title: String,
        val icon: String
    )
}