package com.aliakseilukin.vpntestandroid.domain.model

sealed interface AppError {
    data object NoInternet : AppError
    data class Http(val code: Int) : AppError
    data object Unknown : AppError
}

fun AppError.toUiMessage(): String {
    return when (this) {
        AppError.NoInternet -> "Please check your internet connection."
        is AppError.Http -> "Server error: $code"
        AppError.Unknown -> "Something went wrong."
    }
}