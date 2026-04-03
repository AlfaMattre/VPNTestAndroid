package com.aliakseilukin.vpntestandroid.domain.usecase

import com.aliakseilukin.vpntestandroid.domain.model.StubVpnStatus
import com.aliakseilukin.vpntestandroid.domain.repository.StubVpnRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveVpnStatusUseCase @Inject constructor(
    private val vpnRepository: StubVpnRepository
) {
    operator fun invoke(): StateFlow<StubVpnStatus> = vpnRepository.vpnStatus
}