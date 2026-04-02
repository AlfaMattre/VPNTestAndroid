package com.aliakseilukin.vpntestandroid.presentation.home

import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

data class HomeUiState(
    val countries: List<CountryUI> = emptyList()
)
