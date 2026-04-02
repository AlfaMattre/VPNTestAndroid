package com.aliakseilukin.vpntestandroid.data.service

import com.aliakseilukin.vpntestandroid.domain.model.AppError
import com.aliakseilukin.vpntestandroid.domain.model.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException


suspend inline fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    crossinline apiCall: suspend () -> T
): ResultState<T> {
    return withContext(dispatcher) {
        try {
            ResultState.Success(apiCall())
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            ResultState.Error(AppError.NoInternet)
        } catch (e: HttpException) {
            ResultState.Error(AppError.Http(e.code()))
        } catch (e: Exception) {
            ResultState.Error(AppError.Unknown)
        }
    }
}