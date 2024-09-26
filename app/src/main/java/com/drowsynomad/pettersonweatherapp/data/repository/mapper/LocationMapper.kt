package com.drowsynomad.pettersonweatherapp.data.repository.mapper

import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.LocationEntity
import com.drowsynomad.pettersonweatherapp.data.models.Location

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

fun LocationEntity.toLocation(): Location =
    Location(this.place)
