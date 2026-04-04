package com.aliakseilukin.vpntestandroid.presentation.home

import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnStatus
import com.aliakseilukin.vpntestandroid.presentation.model.CountrySectionUI
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

data class HomeUIState(
    val isCountriesLoading: Boolean = false,
    val countrySections: List<CountrySectionUI> = emptyList(),
    val selectedCountry: CountryUI? = null,
    val vpnStatus: StubVpnStatus = StubVpnStatus.Disconnected,
    val connectedSeconds: Int = 0,
    val countriesErrorMessage: String? = null,
    val vpnErrorMessage: String? = null,
    val isCountryBottomSheetVisible: Boolean = false,
    val isOnline: Boolean = true
)
