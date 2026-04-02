package com.aliakseilukin.vpntestandroid.data.data_source

import com.aliakseilukin.vpntestandroid.data.model.CountryResponse
import com.aliakseilukin.vpntestandroid.data.service.CountriesApi
import javax.inject.Inject

class CountriesDataSourceImpl@Inject constructor(
    private val apiService: CountriesApi
) : CountriesDataSource {

    override suspend fun getCountries(): List<CountryResponse> {
        return apiService.getCountries()
    }
}