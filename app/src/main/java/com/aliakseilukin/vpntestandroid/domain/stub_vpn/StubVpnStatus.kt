package com.aliakseilukin.vpntestandroid.domain.stub_vpn

sealed interface StubVpnStatus {
    data object Disconnected : StubVpnStatus
    data object Connecting : StubVpnStatus
    data object Connected : StubVpnStatus
}