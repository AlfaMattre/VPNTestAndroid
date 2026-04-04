package com.aliakseilukin.vpntestandroid.data.repository

import com.aliakseilukin.vpntestandroid.data.data_source.remote.StubVpnDataSource
import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.network_monitor.NetworkMonitor
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnConnectResult
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnStatus
import com.aliakseilukin.vpntestandroid.domain.repository.StubVpnRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class StubVpnRepositoryImpl @Inject constructor(
    private val vpnDataSource: StubVpnDataSource,
    private val networkMonitor: NetworkMonitor
) : StubVpnRepository {

    override val vpnStatus: StateFlow<StubVpnStatus> = vpnDataSource.vpnStatus

    override suspend fun connect(country: Country): StubVpnConnectResult {
        if (!networkMonitor.isCurrentlyOnline()) return StubVpnConnectResult.Error(null)

        return when (vpnStatus.value) {
            StubVpnStatus.Connected,
            StubVpnStatus.Connecting -> {
                StubVpnConnectResult.Error("VPN is already active.")
            }

            StubVpnStatus.Disconnected -> {
                vpnDataSource.setStatus(StubVpnStatus.Connecting)
                delay(2000)

                if (!networkMonitor.isCurrentlyOnline()) {
                    vpnDataSource.setStatus(StubVpnStatus.Disconnected)
                    return StubVpnConnectResult.Error("Internet connection was lost.")
                }

                val blockedCountries = setOf(
                    "Japan"
                )

                if (country.name in blockedCountries) {
                    vpnDataSource.setStatus(StubVpnStatus.Disconnected)
                    StubVpnConnectResult.Error("VPN is not available for ${country.name}.")
                } else {
                    vpnDataSource.setStatus(StubVpnStatus.Connected)
                    StubVpnConnectResult.Success
                }
            }
        }
    }

    override suspend fun disconnect() {
        if (vpnStatus.value == StubVpnStatus.Connected) {
            vpnDataSource.setStatus(StubVpnStatus.Disconnected)
        }
    }
}