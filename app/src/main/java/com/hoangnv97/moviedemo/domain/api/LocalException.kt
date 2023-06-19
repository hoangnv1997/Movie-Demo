package com.hoangnv97.moviedemo.domain.api

class LocalException(val status: LocalExceptionStatus, val title: String?, message: String?) :
    Exception(message)

enum class LocalExceptionStatus(val value: Int) {
    INVALID_LOGIN(1000),
    INVALID_REGISTER(1001);
}
