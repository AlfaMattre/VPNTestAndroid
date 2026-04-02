package com.aliakseilukin.vpntestandroid.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliakseilukin.vpntestandroid.data.model.local.CountryEntity

@Dao
interface CountriesDao {

    @Query("SELECT * FROM countries")
    suspend fun getCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("DELETE FROM countries")
    suspend fun clearCountries()
}