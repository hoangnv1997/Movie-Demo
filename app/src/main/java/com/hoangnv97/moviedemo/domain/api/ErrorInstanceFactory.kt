package com.hoangnv97.moviedemo.domain.api

import android.content.Context
import com.hoangnv97.moviedemo.Util
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import com.squareup.moshi.Moshi
import io.reactivex.exceptions.CompositeException
import java.io.IOException
import retrofit2.HttpException

class ErrorInstanceFactory {
    companion object {
        fun getErrorInstance(errorObj: ErrorObj, context: Context): ErrorInterface {
            return when (getSimpleThrowable(errorObj.throwable)) {
                is IOException -> {
                    if (Util.isNetworkConnected(context)) {
                        TimeoutError()
                    } else {
                        NetworkError()
                    }
                }
//        is TokenRefreshException -> {
//          createTokenRefreshError(error.throwable) ?: UnknownError()
//        }
                is HttpException -> {
                    createError(errorObj.throwable) ?: UnknownError()
                }
                else -> {
                    UnknownError()
                }
            }
        }

        private fun getSimpleThrowable(throwable: Throwable): Throwable {
            return when (throwable) {
                is CompositeException -> {
                    throwable.exceptions.firstOrNull()?.let {
                        getSimpleThrowable(it)
                    } ?: throwable
                }
                else -> throwable
            }
        }

//    private fun createTokenRefreshError(throwable: Throwable): ErrorInterface? {
//      if (throwable !is TokenRefreshException) return null
//      return try {
//        val moshi = Moshi.Builder().build()
//        val jsonAdapter = moshi.adapter(TokenRefreshError::class.java)
//        jsonAdapter.fromJson(throwable.response()?.errorBody()?.charStream().toString())?.apply {
//          this.status = throwable.code()
//        }
//      } catch (e: Exception) {
//        TokenRefreshError(throwable.code())
//      }
//    }

        private fun createError(throwable: Throwable): ErrorInterface? {
            if (throwable !is HttpException) return null
            return try {
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter(AppServerError::class.java)
                jsonAdapter.fromJson(
                    throwable.response()?.errorBody()?.charStream().toString()
                )?.apply {
                    this.status = throwable.code()
                }
            } catch (e: Exception) {
                AppServerError(throwable.code())
            }
        }
    }
}
