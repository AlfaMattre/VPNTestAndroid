package com.aliakseilukin.vpntestandroid.data.data_source.remote

import com.aliakseilukin.vpntestandroid.domain.model.StubVpnStatus
import kotlinx.coroutines.flow.StateFlow

interface StubVpnDataSource {
    val vpnStatus: StateFlow<StubVpnStatus>

    suspend fun setStatus(status: StubVpnStatus)
}