package com.aliakseilukin.vpntestandroid.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliakseilukin.vpntestandroid.R
import com.aliakseilukin.vpntestandroid.presentation.model.CountrySectionUI
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI
import com.aliakseilukin.vpntestandroid.presentation.model.SectionTitle

@Composable
fun CountryBottomSheet(
    isLoading: Boolean,
    errorMessage: String?,
    sections: List<CountrySectionUI>,
    selectedCountry: CountryUI?,
    onRetryClick: () -> Unit,
    onCountryClick: (CountryUI) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.bottom_sheet_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                BottomSheetErrorContent(
                    message = errorMessage,
                    onRetryClick = onRetryClick
                )
            }

            sections.isEmpty() -> {
                BottomSheetEmptyContent()
            }

            else -> {
                LazyColumn {
                    sections.forEach { section ->
                        item {
                            CountriesSectionHeader(
                                title = when (section.title) {
                                    SectionTitle.PRIMARY -> stringResource(R.string.section_countries_primary)
                                    else -> stringResource(R.string.section_countries_others)
                                }
                            )
                        }

                        items(section.countries) { country ->
                            CountrySelectorItem(
                                country = country,
                                isSelected = selectedCountry?.name == country.name,
                                onClick = { onCountryClick(country) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomSheetEmptyContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.error_counties_empty),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun BottomSheetErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = onRetryClick) {
            Text(text = stringResource(R.string.button_error_retry))
        }
    }
}