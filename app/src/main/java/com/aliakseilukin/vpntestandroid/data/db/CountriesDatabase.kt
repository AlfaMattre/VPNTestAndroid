package com.aliakseilukin.vpntestandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliakseilukin.vpntestandroid.data.model.local.CountryEntity

@Database(
    entities = [CountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CountriesDatabase : RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
}