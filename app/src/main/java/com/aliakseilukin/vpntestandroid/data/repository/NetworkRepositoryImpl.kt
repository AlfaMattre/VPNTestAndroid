package com.aliakseilukin.vpntestandroid.data.repository

import com.aliakseilukin.vpntestandroid.data.data_source.local.CountriesLocalDataSource
import com.aliakseilukin.vpntestandroid.data.data_source.remote.CountriesDataSource
import com.aliakseilukin.vpntestandroid.data.mapper.local.toCountry
import com.aliakseilukin.vpntestandroid.data.mapper.local.toEntity
import com.aliakseilukin.vpntestandroid.data.mapper.remote.toCountry
import com.aliakseilukin.vpntestandroid.data.service.safeApiCall
import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.model.ResultState
import com.aliakseilukin.vpntestandroid.domain.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val countriesDataSource: CountriesDataSource,
    private val countriesLocalDataSource: CountriesLocalDataSource,
) : NetworkRepository {

    override suspend fun getCountries(): ResultState<List<Country>> {
        return when (
            val remoteResult = safeApiCall(Dispatchers.IO) {
                countriesDataSource.getCountries().map { it.toCountry() }
            }
        ) {
            is ResultState.Success -> {
                val countries = remoteResult.data
                countriesLocalDataSource.saveCountries(countries.map { it.toEntity() })
                ResultState.Success(countries)
            }

            is ResultState.Error -> {
                val cachedCountries = countriesLocalDataSource.getCountries().map { it.toCountry() }

                if (cachedCountries.isNotEmpty()) {
                    ResultState.Success(cachedCountries)
                } else {
                    remoteResult
                }
            }
        }
    }
}