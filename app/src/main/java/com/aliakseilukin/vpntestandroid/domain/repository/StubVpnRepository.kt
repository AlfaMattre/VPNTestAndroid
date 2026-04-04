package com.aliakseilukin.vpntestandroid.domain.repository

import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnConnectResult
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnStatus
import kotlinx.coroutines.flow.StateFlow

interface StubVpnRepository {
    val vpnStatus: StateFlow<StubVpnStatus>

    suspend fun connect(country: Country): StubVpnConnectResult
    suspend fun disconnect()
}