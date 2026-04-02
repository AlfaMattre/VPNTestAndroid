package com.aliakseilukin.vpntestandroid.data.service

import com.aliakseilukin.vpntestandroid.data.model.remote.CountryResponse
import retrofit2.http.GET

interface CountriesApi {

    @GET("v3.1/all?fields=name,flags")
    suspend fun getCountries(): List<CountryResponse>
}