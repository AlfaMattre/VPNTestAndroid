package com.aliakseilukin.vpntestandroid.data.data_source.local

import com.aliakseilukin.vpntestandroid.data.db.CountriesDao
import com.aliakseilukin.vpntestandroid.data.model.local.CountryEntity
import javax.inject.Inject

class CountriesLocalDataSourceImpl @Inject constructor(
    private val countriesDao: CountriesDao
) : CountriesLocalDataSource {

    override suspend fun getCountries(): List<CountryEntity> {
        return countriesDao.getCountries()
    }

    override suspend fun saveCountries(countries: List<CountryEntity>) {
        countriesDao.insertCountries(countries)
    }

    override suspend fun clearCountries() {
        countriesDao.clearCountries()
    }
}