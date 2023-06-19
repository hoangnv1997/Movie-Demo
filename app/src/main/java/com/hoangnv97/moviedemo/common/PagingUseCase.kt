package com.hoangnv97.moviedemo.common

abstract class PagingUseCase<T, in Params> {

    protected abstract fun buildUseCase(params: Params): T

    fun execute(params: Params): T {
        return buildUseCase(params)
    }
}
