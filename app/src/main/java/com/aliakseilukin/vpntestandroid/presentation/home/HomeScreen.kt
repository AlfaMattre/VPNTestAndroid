package com.aliakseilukin.vpntestandroid.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliakseilukin.vpntestandroid.presentation.components.CountryBottomSheet
import com.aliakseilukin.vpntestandroid.presentation.components.Toolbar
import com.aliakseilukin.vpntestandroid.presentation.components.VpnSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (state.isCountryBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(HomeEvent.DismissCountryBottomSheet)
            },
            sheetState = bottomSheetState
        ) {
            CountryBottomSheet(
                isLoading = state.isCountriesLoading,
                errorMessage = state.countriesErrorMessage,
                sections = state.countrySections,
                selectedCountry = state.selectedCountry,
                onRetryClick = { viewModel.onEvent(HomeEvent.RetryCountriesClick) },
                onCountryClick = { country ->
                    viewModel.onEvent(HomeEvent.CountrySelected(country))
                }
            )
        }
    }

    Scaffold(
        topBar = { Toolbar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                VpnSection(
                    vpnStatus = state.vpnStatus,
                    connectedSeconds = state.connectedSeconds,
                    selectedCountry = state.selectedCountry,
                    vpnErrorMessage = state.vpnErrorMessage,
                    isOnline = state.isOnline,
                    onVpnClick = { viewModel.onEvent(HomeEvent.VpnButtonClick) },
                    onSelectCountryClick = { viewModel.onEvent(HomeEvent.CountrySelectorClick) },
                    onDismissError = { viewModel.onEvent(HomeEvent.ClearVpnError) }
                )
            }
        }
    }
}