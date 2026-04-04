package com.aliakseilukin.vpntestandroid.domain.stub_vpn

sealed interface StubVpnConnectResult {
    data object Success : StubVpnConnectResult
    data class Error(val message: String?) : StubVpnConnectResult
}