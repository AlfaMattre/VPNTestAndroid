package com.aliakseilukin.vpntestandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.model.ResultState
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnConnectResult
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnStatus
import com.aliakseilukin.vpntestandroid.domain.model.toUiMessage
import com.aliakseilukin.vpntestandroid.domain.network_monitor.NetworkMonitor
import com.aliakseilukin.vpntestandroid.domain.usecase.ConnectToVpnUseCase
import com.aliakseilukin.vpntestandroid.domain.usecase.DisconnectFromVpnUseCase
import com.aliakseilukin.vpntestandroid.domain.usecase.GetCountriesUseCase
import com.aliakseilukin.vpntestandroid.domain.usecase.ObserveVpnStatusUseCase
import com.aliakseilukin.vpntestandroid.presentation.mapper.toCountry
import com.aliakseilukin.vpntestandroid.presentation.mapper.toCountryUi
import com.aliakseilukin.vpntestandroid.presentation.model.CountrySectionUI
import com.aliakseilukin.vpntestandroid.presentation.model.CountryUI
import com.aliakseilukin.vpntestandroid.presentation.model.SectionTitle
import com.aliakseilukin.vpntestandroid.presentation.utils.PrimaryCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    observeVpnStatusUseCase: ObserveVpnStatusUseCase,
    private val connectToVpnUseCase: ConnectToVpnUseCase,
    private val disconnectFromVpnUseCase: DisconnectFromVpnUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUIState())
    val state: StateFlow<HomeUIState> = _state.asStateFlow()

    private var vpnTimerJob: Job? = null

    init {
        observeVpnStatus(observeVpnStatusUseCase)
        observeInternet()
        loadCountries()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.VpnButtonClick -> handleVpnButtonClick()
            HomeEvent.RetryCountriesClick -> loadCountries()
            HomeEvent.CountrySelectorClick -> showCountryBottomSheet()
            HomeEvent.DismissCountryBottomSheet -> hideCountryBottomSheet()
            is HomeEvent.CountrySelected -> handleCountrySelected(event.country)
            HomeEvent.ClearVpnError -> clearVpnError()
        }
    }

    private fun observeInternet() {
        viewModelScope.launch {
            networkMonitor.isOnline.collectLatest { isOnline ->
                _state.update { it.copy(isOnline = isOnline) }

                if (!isOnline && state.value.vpnStatus == StubVpnStatus.Connected) {
                    disconnectFromVpnUseCase()
                }
            }
        }
    }

    private fun showCountryBottomSheet() {
        _state.update { it.copy(isCountryBottomSheetVisible = true) }
    }

    private fun hideCountryBottomSheet() {
        _state.update { it.copy(isCountryBottomSheetVisible = false) }
    }

    private fun clearVpnError() {
        _state.update { it.copy(vpnErrorMessage = null) }
    }

    private fun handleCountrySelected(country: CountryUI) {
        viewModelScope.launch {
            if (state.value.vpnStatus == StubVpnStatus.Connected) {
                disconnectFromVpnUseCase()
            }

            _state.update {
                it.copy(
                    selectedCountry = country,
                    isCountryBottomSheetVisible = false,
                    vpnErrorMessage = null
                )
            }
        }
    }

    private fun handleVpnButtonClick() {
        viewModelScope.launch {
            when (state.value.vpnStatus) {
                StubVpnStatus.Disconnected -> {
                    val selectedCountry = state.value.selectedCountry
                    if (selectedCountry == null) {
                        _state.update {
                            it.copy(vpnErrorMessage = "Please select a country first.")
                        }
                        return@launch
                    }

                    when (val result = connectToVpnUseCase(selectedCountry.toCountry())) {
                        StubVpnConnectResult.Success -> {
                            _state.update { it.copy(vpnErrorMessage = null) }
                        }

                        is StubVpnConnectResult.Error -> {
                            _state.update { it.copy(vpnErrorMessage = result.message) }
                        }
                    }
                }

                StubVpnStatus.Connected -> {
                    disconnectFromVpnUseCase()
                }

                StubVpnStatus.Connecting -> Unit
            }
        }
    }

    private fun observeVpnStatus(observeVpnStatusUseCase: ObserveVpnStatusUseCase) {
        viewModelScope.launch {
            observeVpnStatusUseCase().collectLatest { status ->
                _state.update { current ->
                    current.copy(vpnStatus = status)
                }
                handleVpnStatusChanged(status)
            }
        }
    }

    private fun handleVpnStatusChanged(status: StubVpnStatus) {
        when (status) {
            StubVpnStatus.Connected -> startVpnTimer()
            StubVpnStatus.Disconnected -> stopVpnTimer(reset = true)
            StubVpnStatus.Connecting -> stopVpnTimer(reset = false)
        }
    }

    private fun startVpnTimer() {
        if (vpnTimerJob?.isActive == true) return

        vpnTimerJob = viewModelScope.launch {
            while (isActive && state.value.vpnStatus == StubVpnStatus.Connected) {
                delay(1_000)
                _state.update { current ->
                    current.copy(connectedSeconds = current.connectedSeconds + 1)
                }
            }
        }
    }

    private fun stopVpnTimer(reset: Boolean) {
        vpnTimerJob?.cancel()
        vpnTimerJob = null

        if (reset) {
            _state.update { current ->
                current.copy(connectedSeconds = 0)
            }
        }
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isCountriesLoading = true,
                    countriesErrorMessage = null
                )
            }

            when (val result: ResultState<List<Country>> = getCountriesUseCase()) {
                is ResultState.Success -> {
                    val countries = result.data.map { it.toCountryUi() }
                    val sections = buildCountrySections(countries)

                    _state.update {
                        it.copy(
                            isCountriesLoading = false,
                            countrySections = sections,
                            selectedCountry = it.selectedCountry
                                ?: sections.firstOrNull()?.countries?.firstOrNull(),
                            countriesErrorMessage = null
                        )
                    }
                }

                is ResultState.Error -> {
                    _state.update {
                        it.copy(
                            isCountriesLoading = false,
                            countriesErrorMessage = result.error.toUiMessage(),
                            countrySections = emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun buildCountrySections(countries: List<CountryUI>): List<CountrySectionUI> {
        val featuredCountries = PrimaryCountries.mapNotNull { featuredName ->
            countries.find { it.name == featuredName }
        }

        val otherCountries = countries.filter { it.name !in PrimaryCountries }

        return buildList {
            if (featuredCountries.isNotEmpty()) {
                add(
                    CountrySectionUI(
                        title = SectionTitle.PRIMARY,
                        countries = featuredCountries
                    )
                )
            }

            if (otherCountries.isNotEmpty()) {
                add(
                    CountrySectionUI(
                        title = SectionTitle.OTHERS,
                        countries = otherCountries
                    )
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        vpnTimerJob?.cancel()
    }
}