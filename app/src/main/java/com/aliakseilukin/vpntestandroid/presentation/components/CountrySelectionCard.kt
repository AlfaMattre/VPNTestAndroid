package com.aliakseilukin.vpntestandroid.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.aliakseilukin.vpntestandroid.R
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

@Composable
fun CountrySelectionCard(
    selectedCountry: CountryUI?,
    onClick: () -> Unit
) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedCountry != null) {
                CountryFlagImage(selectedCountry.flagUrl, selectedCountry.name)

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = selectedCountry.name,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Text(
                    text = stringResource(R.string.error_no_country_selected),
                    modifier = Modifier.weight(1f)
                )
            }

            Text(text = "Change")
        }
    }
}