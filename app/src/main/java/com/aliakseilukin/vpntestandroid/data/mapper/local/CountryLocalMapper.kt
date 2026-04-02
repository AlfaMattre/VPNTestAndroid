package com.aliakseilukin.vpntestandroid.data.mapper.local

import com.aliakseilukin.vpntestandroid.data.model.local.CountryEntity
import com.aliakseilukin.vpntestandroid.domain.model.Country

fun CountryEntity.toCountry(): Country {
    return Country(
        name = name,
        flagUrl = flagUrl
    )
}

fun Country.toEntity(): CountryEntity {
    return CountryEntity(
        name = name,
        flagUrl = flagUrl
    )
}