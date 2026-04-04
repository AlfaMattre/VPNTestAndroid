package com.aliakseilukin.vpntestandroid.domain.usecase

import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnConnectResult
import com.aliakseilukin.vpntestandroid.domain.repository.StubVpnRepository
import javax.inject.Inject

class ConnectToVpnUseCase @Inject constructor(
    private val vpnRepository: StubVpnRepository
) {
    suspend operator fun invoke(country: Country): StubVpnConnectResult {
        return vpnRepository.connect(country)
    }
}