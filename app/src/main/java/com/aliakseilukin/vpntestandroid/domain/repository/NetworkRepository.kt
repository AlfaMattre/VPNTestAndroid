package com.aliakseilukin.vpntestandroid.domain.repository

import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.model.ResultState

interface NetworkRepository {
    suspend fun getCountries(): ResultState<List<Country>>
}