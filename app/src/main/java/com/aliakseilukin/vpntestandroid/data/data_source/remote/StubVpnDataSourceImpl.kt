package com.aliakseilukin.vpntestandroid.data.data_source.remote

import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class StubVpnDataSourceImpl @Inject constructor() : StubVpnDataSource {

    private val _vpnStatus = MutableStateFlow<StubVpnStatus>(StubVpnStatus.Disconnected)
    override val vpnStatus: StateFlow<StubVpnStatus> = _vpnStatus.asStateFlow()

    override suspend fun setStatus(status: StubVpnStatus) {
        _vpnStatus.value = status
    }
}