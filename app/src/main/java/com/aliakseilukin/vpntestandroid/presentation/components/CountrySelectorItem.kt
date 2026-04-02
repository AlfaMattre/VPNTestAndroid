package com.aliakseilukin.vpntestandroid.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI

@Composable
fun CountrySelectorItem(
    country: CountryUI,
    modifier: Modifier = Modifier,
    onCountryClick: (CountryUI) -> Unit
) {
    Surface(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onCountryClick(country) },
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier.clip(CircleShape)
            ) {
                AsyncImage(
                    model = country.flagUrl,
                    contentDescription = country.name,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text = country.name,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}