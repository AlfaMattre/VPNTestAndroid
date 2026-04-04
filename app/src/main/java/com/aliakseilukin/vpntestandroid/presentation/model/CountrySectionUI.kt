package com.aliakseilukin.vpntestandroid.presentation.model

data class CountrySectionUI(
    val title: SectionTitle = SectionTitle.PRIMARY,
    val countries: List<CountryUI> = emptyList()
)

enum class SectionTitle {
    PRIMARY, OTHERS
}
