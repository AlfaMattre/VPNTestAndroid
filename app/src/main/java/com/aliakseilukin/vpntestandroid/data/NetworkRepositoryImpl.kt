package com.aliakseilukin.vpntestandroid.data

import com.aliakseilukin.vpntestandroid.data.data_source.CountriesDataSource
import com.aliakseilukin.vpntestandroid.data.mapper.toCountry
import com.aliakseilukin.vpntestandroid.domain.NetworkRepository
import com.aliakseilukin.vpntestandroid.domain.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val remoteDataSource: CountriesDataSource,
) : NetworkRepository {

    override suspend fun getCountries(): List<Country> = withContext(Dispatchers.IO) {
        remoteDataSource.getCountries().map { it.toCountry() }
    }
}