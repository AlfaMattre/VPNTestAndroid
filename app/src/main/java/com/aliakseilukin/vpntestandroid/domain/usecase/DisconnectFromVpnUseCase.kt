package com.aliakseilukin.vpntestandroid.domain.usecase

import com.aliakseilukin.vpntestandroid.domain.repository.StubVpnRepository
import javax.inject.Inject

class DisconnectFromVpnUseCase @Inject constructor(
    private val vpnRepository: StubVpnRepository
) {
    suspend operator fun invoke() {
        vpnRepository.disconnect()
    }
}