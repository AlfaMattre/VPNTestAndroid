package com.aliakseilukin.vpntestandroid.domain.repository

import com.aliakseilukin.vpntestandroid.domain.model.StubVpnStatus
import kotlinx.coroutines.flow.StateFlow

interface StubVpnRepository {
    val vpnStatus: StateFlow<StubVpnStatus>

    suspend fun connect()
    suspend fun disconnect()
}