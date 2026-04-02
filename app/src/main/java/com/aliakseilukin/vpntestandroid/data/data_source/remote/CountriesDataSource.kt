package com.aliakseilukin.vpntestandroid.data.data_source.remote

import com.aliakseilukin.vpntestandroid.data.model.remote.CountryResponse

interface CountriesDataSource {
    suspend fun getCountries(): List<CountryResponse>
}