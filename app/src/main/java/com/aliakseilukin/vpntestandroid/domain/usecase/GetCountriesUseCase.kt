package com.aliakseilukin.vpntestandroid.domain.usecase

import com.aliakseilukin.vpntestandroid.domain.NetworkRepository
import com.aliakseilukin.vpntestandroid.domain.model.Country
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val apiRepository: NetworkRepository
) {
    suspend operator fun invoke(): List<Country> = apiRepository.getCountries()
}