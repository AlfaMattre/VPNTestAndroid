package com.aliakseilukin.vpntestandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliakseilukin.vpntestandroid.domain.model.ResultState
import com.aliakseilukin.vpntestandroid.domain.model.StubVpnStatus
import com.aliakseilukin.vpntestandroid.domain.model.toUiMessage
import com.aliakseilukin.vpntestandroid.domain.usecase.ConnectToVpnUseCase
import com.aliakseilukin.vpntestandroid.domain.usecase.DisconnectFromVpnUseCase
import com.aliakseilukin.vpntestandroid.domain.usecase.GetCountriesUseCase
import com.aliakseilukin.vpntestandroid.domain.usecase.ObserveVpnStatusUseCase
import com.aliakseilukin.vpntestandroid.presentation.mapper.toCountryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    observeVpnStatusUseCase: ObserveVpnStatusUseCase,
    private val connectToVpnUseCase: ConnectToVpnUseCase,
    private val disconnectFromVpnUseCase: DisconnectFromVpnUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        observeVpnStatus(observeVpnStatusUseCase)
        loadCountries()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnVpnButtonClick -> handleVpnButtonClick()
            HomeEvent.OnRetryCountriesClick -> loadCountries()
        }
    }

    fun retryLoadingCountries() {
        loadCountries()
    }

    private fun handleVpnButtonClick() {
        viewModelScope.launch {
            when (state.value.vpnStatus) {
                StubVpnStatus.Disconnected -> connectToVpnUseCase()
                StubVpnStatus.Connected -> disconnectFromVpnUseCase()
                StubVpnStatus.Connecting -> Unit
            }
        }
    }

    private fun observeVpnStatus(observeVpnStatusUseCase: ObserveVpnStatusUseCase) {
        viewModelScope.launch {
            observeVpnStatusUseCase().collect { status ->
                _state.update { state ->
                    state.copy(vpnStatus = status)
                }
            }
        }
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            when (val result = getCountriesUseCase()) {
                is ResultState.Success -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            countries = result.data.map { country -> country.toCountryUi() },
                            errorMessage = null
                        )
                    }
                }

                is ResultState.Error -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = result.error.toUiMessage()
                        )
                    }
                }
            }
        }
    }
}