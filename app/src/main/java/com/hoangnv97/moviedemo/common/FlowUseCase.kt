package com.hoangnv97.moviedemo.common

import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.di.IOCoroutineScope
import com.hoangnv97.moviedemo.di.IODispatcher
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import com.hoangnv97.moviedemo.infra.wrapper.Retry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

abstract class FlowUseCase<T, in Params> constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
    @IOCoroutineScope val ioCoroutineScope: CoroutineScope
) {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.stateIn(
        ioCoroutineScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )

    private val _failed = MutableStateFlow<ErrorObj?>(null)
    val failed: StateFlow<ErrorObj?> = _failed.stateIn(
        ioCoroutineScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    protected abstract val apiType: ApiEnum
    protected abstract suspend fun buildUseCase(params: Params): T
    fun execute(params: Params, retry: Retry? = null): Flow<T> {
        return flow {
            _loading.emit(true)
            delay(1000)
            val response = buildUseCase(params)
            emit(response)
            _loading.emit(false)
        }.catch {
            val error = ErrorObj(apiType, it)
            error.retry = retry
            _failed.emit(error)
            _loading.emit(false)
        }.flowOn(ioDispatcher)
    }
}
