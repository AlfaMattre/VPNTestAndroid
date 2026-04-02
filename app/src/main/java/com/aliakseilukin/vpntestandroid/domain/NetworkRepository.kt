package com.aliakseilukin.vpntestandroid.domain

import com.aliakseilukin.vpntestandroid.domain.model.Country

interface NetworkRepository {
    suspend fun getCountries(): List<Country>
}