package com.aliakseilukin.vpntestandroid.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(
    @SerialName("name") val name: NameResponse? = null,
    @SerialName("flags") val flags: FlagsResponse? = null
)

@Serializable
data class NameResponse(
    @SerialName("common") val common: String? = null,
    @SerialName("official") val official: String? = null
)

@Serializable
data class FlagsResponse(
    @SerialName("png") val png: String? = null,
    @SerialName("svg") val svg: String? = null,
    @SerialName("alt") val alt: String? = null
)
