package com.aliakseilukin.vpntestandroid.presentation.home

import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

sealed interface HomeEvent {
    data object VpnButtonClick : HomeEvent
    data object RetryCountriesClick : HomeEvent
    data object CountrySelectorClick : HomeEvent
    data object DismissCountryBottomSheet : HomeEvent
    data class CountrySelected(val country: CountryUI) : HomeEvent
    data object ClearVpnError : HomeEvent
}