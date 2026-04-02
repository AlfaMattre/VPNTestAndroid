package com.aliakseilukin.vpntestandroid.data.mapper

import com.aliakseilukin.vpntestandroid.data.model.CountryResponse
import com.aliakseilukin.vpntestandroid.domain.model.Country

fun CountryResponse.toCountry(): Country {
    return Country(
        name = name?.common.orEmpty(),
        flagUrl = flags?.png ?: flags?.svg.orEmpty()
    )
}