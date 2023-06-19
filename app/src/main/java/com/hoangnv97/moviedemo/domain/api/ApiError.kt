package com.hoangnv97.moviedemo.domain.api

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.hoangnv97.moviedemo.infra.ApiEnum

abstract class ApiError(val api: ApiEnum, val targetClassName: String? = null) {
    data class Result(val api: ApiEnum, val errorType: ErrorType)

    sealed class ErrorType {
        object Unknown : ErrorType()

        object NotFound : ErrorType()

        object AutoRetry : ErrorType()

        data class TokenRefresh(val statusCode: Int?) : ErrorType()

        data class Local(val status: Int?, val title: String? = null, val message: String? = null) :
            ErrorType()

        data class JustClose(val title: String? = null, val message: String? = null) : ErrorType()

        data class Back(val title: String? = null, val message: String? = null) : ErrorType()

        data class BackToLogin(val title: String? = null, val message: String? = null) : ErrorType()

        data class Retry(val title: String? = null, val message: String? = null) : ErrorType()

        data class RetryBackToLogin(val title: String? = null, val message: String? = null) :
            ErrorType()

        data class RetryToContinue(val title: String? = null, val message: String? = null) :
            ErrorType()

        data class RetryScreen(val title: String? = null, val message: String? = null) : ErrorType()

//    data class Validation(
//      val errors: List<AppServerError.Error>,
//      val unnecessaryCorrectly: Boolean = false
//    ) : ErrorType()
    }

    abstract fun getErrorType(context: Context, error: ErrorInterface): ErrorType

    companion object {
        inline fun <reified T : Fragment> getTargetClassName(): String = T::class.java.name
        fun getTargetClassName(fragment: Fragment): String = fragment::class.java.name
        fun getTargetClassName(activity: Activity): String = activity::class.java.name
    }
}

interface ErrorInterface {
    var status: Int
}

data class TimeoutError(override var status: Int = 0) : ErrorInterface
data class NetworkError(override var status: Int = 0) : ErrorInterface
data class UnknownError(override var status: Int = 0) : ErrorInterface
