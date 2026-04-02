package com.aliakseilukin.vpntestandroid.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliakseilukin.vpntestandroid.presentation.components.CountrySelectorItem
import com.aliakseilukin.vpntestandroid.presentation.components.ErrorContent
import com.aliakseilukin.vpntestandroid.presentation.components.Toolbar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    when {
        state.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            ErrorContent(
                message = state.errorMessage,
                onRetry = viewModel::loadCountries,
                modifier = modifier
            )
        }

        else -> {
            Scaffold(
                topBar = { Toolbar() }
            ) { innerPadding ->
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(state.countries) { country ->
                        CountrySelectorItem(country = country) { country ->

                        }
                    }
                }
            }
        }
    }
}