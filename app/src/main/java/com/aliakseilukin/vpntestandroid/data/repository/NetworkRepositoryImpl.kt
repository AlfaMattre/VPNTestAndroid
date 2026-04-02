package com.aliakseilukin.vpntestandroid.data.repository

import com.aliakseilukin.vpntestandroid.data.data_source.CountriesDataSource
import com.aliakseilukin.vpntestandroid.data.mapper.toCountry
import com.aliakseilukin.vpntestandroid.data.service.safeApiCall
import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.model.ResultState
import com.aliakseilukin.vpntestandroid.domain.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val countriesDataSource: CountriesDataSource,
) : NetworkRepository {

    override suspend fun getCountries(): ResultState<List<Country>> {
        return safeApiCall(Dispatchers.IO) {
            countriesDataSource.getCountries().map { it.toCountry() }
        }
    }
}