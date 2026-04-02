package com.aliakseilukin.vpntestandroid.presentation.home

import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

data class HomeUiState(
    val isLoading: Boolean = false,
    val countries: List<CountryUI> = emptyList(),
    val errorMessage: String? = null
)
