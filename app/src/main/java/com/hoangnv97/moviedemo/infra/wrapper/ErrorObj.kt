package com.hoangnv97.moviedemo.infra.wrapper

import com.hoangnv97.moviedemo.infra.ApiEnum

typealias Retry = () -> Unit

data class ErrorObj(val api: ApiEnum, val throwable: Throwable) : RuntimeException(throwable) {
    var retry: Retry? = null
}
