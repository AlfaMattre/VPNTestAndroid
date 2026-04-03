package com.aliakseilukin.vpntestandroid.presentation.home

sealed interface HomeEvent {
    data object OnVpnButtonClick : HomeEvent
    data object OnRetryCountriesClick : HomeEvent
}