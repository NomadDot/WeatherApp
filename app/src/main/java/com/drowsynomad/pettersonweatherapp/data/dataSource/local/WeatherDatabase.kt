package com.drowsynomad.pettersonweatherapp.data.dataSource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.dao.WeatherDao
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.LocationEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.WeatherEntity

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

@Database(
    version = 1,
    entities = [
        LocationEntity::class,
        WeatherEntity::class
    ]
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        private var databaseInstance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            if(databaseInstance == null)
                databaseInstance = Room
                    .databaseBuilder(context, WeatherDatabase::class.java, "weather_db")
                    .allowMainThreadQueries()
                    .build()

            return databaseInstance as WeatherDatabase
        }
    }
}