package com.aliakseilukin.vpntestandroid.domain.model

interface StubVpnStatus {
    data object Disconnected : StubVpnStatus
    data object Connecting : StubVpnStatus
    data object Connected : StubVpnStatus
}