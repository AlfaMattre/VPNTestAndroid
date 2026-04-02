package com.aliakseilukin.vpntestandroid.presentation.mapper

import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

fun Country.toCountryUi(): CountryUI {
    return CountryUI(
        name = name,
        flagUrl = flagUrl
    )
}