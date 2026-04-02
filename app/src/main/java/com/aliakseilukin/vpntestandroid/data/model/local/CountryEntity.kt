package com.aliakseilukin.vpntestandroid.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val name: String,
    val flagUrl: String
)
