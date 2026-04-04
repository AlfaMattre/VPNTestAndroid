package com.aliakseilukin.vpntestandroid.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliakseilukin.vpntestandroid.R
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnStatus
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI
import com.aliakseilukin.vpntestandroid.presentation.utils.formatVpnDuration

@Composable
fun VpnSection(
    vpnStatus: StubVpnStatus,
    connectedSeconds: Int,
    selectedCountry: CountryUI?,
    vpnErrorMessage: String?,
    isOnline: Boolean,
    onVpnClick: () -> Unit,
    onSelectCountryClick: () -> Unit,
    onDismissError: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (!isOnline) {
            ErrorMessageCard(
                message = stringResource(R.string.error_device_offline)
            )
        }

        if (vpnErrorMessage != null) {
            ErrorMessageCard(
                message = vpnErrorMessage,
                onDismiss = onDismissError
            )
        }

        Text(
            text = when (vpnStatus) {
                StubVpnStatus.Disconnected -> stringResource(R.string.vpn_status_disconnected)
                StubVpnStatus.Connecting -> stringResource(R.string.vpn_status_connecting)
                StubVpnStatus.Connected -> stringResource(R.string.vpn_status_connected)
            },
            style = MaterialTheme.typography.titleMedium,
            color = when (vpnStatus) {
                StubVpnStatus.Connected -> MaterialTheme.colorScheme.tertiary
                else -> MaterialTheme.colorScheme.onBackground
            }
        )

        Text(
            text = connectedSeconds.formatVpnDuration(),
            style = MaterialTheme.typography.bodyLarge
        )

        VpnButton(
            vpnStatus = vpnStatus,
            onClick = onVpnClick
        )

        CountrySelectionCard(
            selectedCountry = selectedCountry,
            onClick = onSelectCountryClick
        )
    }
}