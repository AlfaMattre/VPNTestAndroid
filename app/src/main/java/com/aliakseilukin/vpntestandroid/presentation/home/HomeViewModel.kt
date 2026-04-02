package com.aliakseilukin.vpntestandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliakseilukin.vpntestandroid.domain.model.Country
import com.aliakseilukin.vpntestandroid.domain.model.ResultState
import com.aliakseilukin.vpntestandroid.domain.model.toUiMessage
import com.aliakseilukin.vpntestandroid.domain.usecase.GetCountriesUseCase
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
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null
            )

            when (val result = getCountriesUseCase()) {
                is ResultState.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        countries = result.data.map { it.toCountryUi() },
                        errorMessage = null
                    )
                }

                is ResultState.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.error.toUiMessage()
                    )
                }
            }
        }
    }
}