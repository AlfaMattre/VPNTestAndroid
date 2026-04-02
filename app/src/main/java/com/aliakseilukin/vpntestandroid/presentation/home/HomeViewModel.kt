package com.aliakseilukin.vpntestandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        get()
    }

    fun get() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    countries = getCountriesUseCase().map { country -> country.toCountryUi() }
                )
            }
        }
    }
}