package com.hoangnv97.moviedemo.domain.api

import android.content.Context
import com.hoangnv97.moviedemo.infra.ApiEnum

class ApiCreditsError(targetClassName: String) : ApiError(ApiEnum.API_CAST_LIST, targetClassName) {
    override fun getErrorType(context: Context, error: ErrorInterface): ErrorType {
        return when ((error as? AppServerError)?.status) {
            400 -> {
                ErrorType.JustClose(
                    title = "Error",
                    message = "Error"
                )
            }
            else -> {
                ErrorType.Unknown
            }
        }
    }
}
