package com.aliakseilukin.vpntestandroid.data.data_source.local

import com.aliakseilukin.vpntestandroid.data.model.local.CountryEntity

interface CountriesLocalDataSource {
    suspend fun getCountries(): List<CountryEntity>
    suspend fun saveCountries(countries: List<CountryEntity>)
    suspend fun clearCountries()
}