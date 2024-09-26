package com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

@Entity(
    tableName = "location"
)
data class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "place_name")
    val place: String = "",
)