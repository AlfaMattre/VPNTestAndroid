package com.aliakseilukin.vpntestandroid.data.data_source

import com.aliakseilukin.vpntestandroid.data.model.CountryResponse

interface CountriesDataSource {
    suspend fun getCountries(): List<CountryResponse>
}