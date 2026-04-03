package com.aliakseilukin.vpntestandroid.presentation.home

import com.aliakseilukin.vpntestandroid.domain.model.StubVpnStatus
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

data class HomeUiState(
    val isLoading: Boolean = false,
    val countries: List<CountryUI> = emptyList(),
    val vpnStatus: StubVpnStatus = StubVpnStatus.Disconnected,
    val errorMessage: String? = null
)
