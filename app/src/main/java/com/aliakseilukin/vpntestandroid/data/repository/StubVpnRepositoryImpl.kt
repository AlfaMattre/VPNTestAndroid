package com.aliakseilukin.vpntestandroid.data.repository

import com.aliakseilukin.vpntestandroid.data.data_source.remote.StubVpnDataSource
import com.aliakseilukin.vpntestandroid.domain.model.StubVpnStatus
import com.aliakseilukin.vpntestandroid.domain.repository.StubVpnRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class StubVpnRepositoryImpl @Inject constructor(
    private val vpnDataSource: StubVpnDataSource
) : StubVpnRepository {

    override val vpnStatus: StateFlow<StubVpnStatus> = vpnDataSource.vpnStatus

    override suspend fun connect() {
        when (vpnStatus.value) {
            StubVpnStatus.Connected, StubVpnStatus.Connecting -> return

            StubVpnStatus.Disconnected -> {
                vpnDataSource.setStatus(StubVpnStatus.Connecting)
                delay(2000)
                vpnDataSource.setStatus(StubVpnStatus.Connected)
            }
        }
    }

    override suspend fun disconnect() {
        if (vpnStatus.value == StubVpnStatus.Connected) {
            vpnDataSource.setStatus(StubVpnStatus.Disconnected)
        }
    }
}