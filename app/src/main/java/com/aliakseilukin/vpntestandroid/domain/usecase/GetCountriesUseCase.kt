package com.aliakseilukin.vpntestandroid.domain.usecase

import com.aliakseilukin.vpntestandroid.domain.repository.NetworkRepository
import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.model.ResultState
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val apiRepository: NetworkRepository
) {
    suspend operator fun invoke(): ResultState<List<Country>> = apiRepository.getCountries()
}