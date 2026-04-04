package com.aliakseilukin.vpntestandroid.presentation.mapper

import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

fun CountryUI.toCountry(): Country {
    return Country(
        name = name,
        flagUrl = flagUrl
    )
}