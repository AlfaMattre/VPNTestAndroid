package com.aliakseilukin.vpntestandroid.domain.model

sealed interface ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val error: AppError) : ResultState<Nothing>
}