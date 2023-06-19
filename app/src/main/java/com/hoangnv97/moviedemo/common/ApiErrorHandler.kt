package com.hoangnv97.moviedemo.common

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hoangnv97.moviedemo.domain.api.ApiCreditsError
import com.hoangnv97.moviedemo.domain.api.ApiError
import com.hoangnv97.moviedemo.domain.api.AppServerError
import com.hoangnv97.moviedemo.domain.api.ErrorInstanceFactory
import com.hoangnv97.moviedemo.domain.api.LocalException
import com.hoangnv97.moviedemo.domain.api.NetworkError
import com.hoangnv97.moviedemo.domain.api.TimeoutError
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import com.hoangnv97.moviedemo.presentation.main.dialog.Dialog
import javax.inject.Inject
import timber.log.Timber

class ApiErrorHandler @Inject constructor() {
    private var errorResultListener: ((ApiError.Result) -> Unit)? = null
    private var closeClickListener: ((ApiEnum, ApiError.ErrorType) -> Unit)? = null
    private var showListener: DialogInterface.OnShowListener? = null
    private var dismissListener: DialogInterface.OnDismissListener? = null
    private var retryListener: ((ErrorObj, ApiError.ErrorType) -> Unit)? = null

    fun setOnErrorResultListener(listener: (result: ApiError.Result) -> Unit) {
        errorResultListener = listener
    }

    fun setOnCloseClickListener(listener: (api: ApiEnum, errorType: ApiError.ErrorType) -> Unit) {
        closeClickListener = listener
    }

    fun setOnShowListener(listener: DialogInterface.OnShowListener) {
        showListener = listener
    }

    fun setOnDismissListener(listener: DialogInterface.OnDismissListener) {
        dismissListener = listener
    }

    fun setOnRetryListener(
        listener: ((errorObj: ErrorObj, errorType: ApiError.ErrorType) -> Unit)?
    ) {
        retryListener = listener
    }

    fun show(activity: AppCompatActivity?, errorObj: ErrorObj) {
        activity?.also {
            showInner(
                it.applicationContext,
                ApiError.getTargetClassName(it),
                it.supportFragmentManager, errorObj
            )
        }
    }

    fun show(fragment: Fragment?, errorObj: ErrorObj) {
        fragment?.let {
            showInner(
                it.context,
                ApiError.getTargetClassName(it),
                it.childFragmentManager, errorObj
            )
        }
    }

    fun show(fragment: Fragment?, errorObj: ErrorObj, errorType: ApiError.ErrorType) {
        fragment?.also {
            showInner(
                it.context,
                ApiError.getTargetClassName(it),
                it.childFragmentManager, errorObj, errorType
            )
        }
    }

    fun showLocalError(fragment: Fragment?, errorObj: ErrorObj) {
        val nonNullFragment = fragment ?: return
        val throwable = errorObj.throwable as LocalException
        val localErrorType = ApiError.ErrorType.Local(
            status = throwable.status.value,
            title = throwable.title,
            message = throwable.message
        )
        Dialog.createCloseDialog(
            title = throwable.title,
            message = throwable.message,
            positiveClickListener = closeClickListener(errorObj.api, localErrorType)
        ).run {
            setOnShowListener(showListener)
            setOnDismissListener(dismissListener)
            show(nonNullFragment.childFragmentManager)
        }
    }

    private fun showInner(
        context: Context?,
        targetClassName: String,
        fragmentManager: FragmentManager,
        errorObj: ErrorObj,
        apiErrorType: ApiError.ErrorType? = null
    ) {
        if (context == null) {
            Timber.e("context null")
            return
        }

        when (val errorType = apiErrorType ?: getErrorType(errorObj, context, targetClassName)) {
//      is ApiError.ErrorType.TokenRefresh -> {
//        Dialog.createTokenRefreshError(
//          context,
//          fragmentManager,
//          errorType.statusCode,
//          object : TokenRefreshErrorDialog.RetryListener {
//            override fun onSuccess() {
//              Timber.d("retry success")
//              errorResultListener?.invoke(ApiError.Result(error.api, errorType))
//            }
//          },
//          tokenRepository,
//          appSettingRepository
//        )?.run {
//          setOnShowListener(showListener)
//          setOnDismissListener(dismissListener)
//          show(fragmentManager)
//        }
//      }
            is ApiError.ErrorType.JustClose -> {
                Dialog.createCloseDialog(
                    title = errorType.title,
                    message = errorType.message,
                    positiveClickListener = closeClickListener(errorObj.api, errorType)
                ).run {
                    setOnShowListener(showListener)
                    setOnDismissListener(dismissListener)
                    show(fragmentManager)
                }
            }
            is ApiError.ErrorType.Back -> {
                Dialog.createCloseDialog(
                    title = errorType.title,
                    message = errorType.message,
                    positiveClickListener = closeClickListener(errorObj.api, errorType)
                ).run {
                    setOnShowListener(showListener)
                    setOnDismissListener(dismissListener)
                    show(fragmentManager)
                }
            }
            is ApiError.ErrorType.Retry -> {
                Dialog.createRetryDialog(
                    title = errorType.title,
                    message = errorType.message,
                    positiveClickListener = retryClickListener(errorObj, errorType),
                    negativeClickListener = closeClickListener(errorObj.api, errorType)
                ).run {
                    setOnShowListener(showListener)
                    setOnDismissListener(dismissListener)
                    show(fragmentManager)
                }
            }
            is ApiError.ErrorType.RetryToContinue -> {
                Dialog.createRetryDialogSingleButton(
                    title = errorType.title,
                    message = errorType.message,
                    positiveClickListener = retryClickListener(errorObj, errorType)
                ).run {
                    setOnShowListener(showListener)
                    setOnDismissListener(dismissListener)
                    show(fragmentManager)
                }
            }
            is ApiError.ErrorType.RetryBackToLogin -> {
                Dialog.createRetryDialog(
                    title = errorType.title,
                    message = errorType.message,
                    positiveClickListener = { _, _ -> forceClose(context) },
                    negativeClickListener = closeClickListener(errorObj.api, errorType)
                ).run {
                    setOnShowListener(showListener)
                    setOnDismissListener(dismissListener)
                    show(fragmentManager)
                }
            }
            is ApiError.ErrorType.RetryScreen,
            ApiError.ErrorType.AutoRetry -> {
                retryListener?.invoke(errorObj, errorType) ?: errorObj.retry?.invoke()
            }
            is ApiError.ErrorType.BackToLogin -> {
                Dialog.createCloseDialog(
                    title = errorType.title,
                    message = errorType.message,
                    positiveClickListener = { _, _ -> forceClose(context) }
                ).run {
                    setOnShowListener(showListener)
                    setOnDismissListener(dismissListener)
                    show(fragmentManager)
                }
            }
//      is ApiError.ErrorType.Validation -> {
//        val validationMap = HashMap<String, String>().apply {
//          put("email", "Format error")
//        }
//
//        val hideFieldList = arrayListOf("abc", "cde")
//
//        val errorMessageList = ArrayList<String>()
//        errorType.errors.forEach {
//          it.field?.let { field ->
//            validationMap[field]?.let { key -> errorMessageList.add(key) }
//          }
//        }
//
//        val bottomMessage = if (errorType.unnecessaryCorrectly) {
//          "Please enter."
//        } else {
//          "Please enter correctly."
//        }
//
//        val message =
//          errorMessageList.distinct().filter {
//            if (BuildConfig.DEBUG) true else !hideFieldList.contains(it)
//          }.joinToString(separator = "、")
//        if (message.isNotEmpty()) {
//          Timber.d(message)
//          Dialog.createCloseDialog(
//            message = message + bottomMessage,
//            positiveClickListener = closeClickListener(error.api, errorType)
//          ).run {
//            setOnShowListener(showListener)
//            setOnDismissListener(dismissListener)
//            show(fragmentManager)
//          }
//        }
//        errorResultListener?.invoke(ApiError.Result(error.api, errorType))
//      }
            is ApiError.ErrorType.Unknown -> {
                Dialog.createCloseDialog(
                    message = "An error occurred, please try again.",
                    positiveClickListener = closeClickListener(errorObj.api, errorType)
                ).run {
                    setOnShowListener(showListener)
                    setOnDismissListener(dismissListener)
                    show(fragmentManager)
                }
            }
            else -> {}
        }
    }

    private fun getErrorType(
        errorObj: ErrorObj,
        context: Context,
        targetClassName: String? = null
    ): ApiError.ErrorType {
//    val isHomeTab = isHomeTab(error.api, targetClassName)

        return when (val errorInstance = ErrorInstanceFactory.getErrorInstance(errorObj, context)) {
            is NetworkError -> {
                ApiError.ErrorType.Retry(
                    title = "No internet",
                    message = "Please check the internet again"
                )
            }
            is TimeoutError -> {
                ApiError.ErrorType.Retry(
                    title = "Error",
                    message = "An error occurred, please try again."
                )
            }
//      is TokenRefreshError -> {
//        ApiError.ErrorType.TokenRefresh(errorInstance.status)
//      }
            is AppServerError -> {
                targetClassName ?: return ApiError.ErrorType.Unknown
                (errorInstance as? AppServerError).let { appServerError ->
                    appServerError?.errors?.sortWith(compareBy({ it.code }, { it.field }))
                    Timber.e("${appServerError?.errors}")
                }
                when (errorObj.api) {
                    ApiEnum.API_CAST_LIST -> ApiCreditsError(
                        targetClassName
                    ).getErrorType(context, errorInstance)
                    else -> ApiError.ErrorType.Unknown
                }
            }
            else -> {
                ApiError.ErrorType.Unknown
            }
        }
    }

//  private fun isHomeTab(api: ApiEnum, targetClassName: String?): Boolean {
//    if(targetClassName.isNullOrEmpty()) return false
//    return when(targetClassName) {
//      ApiError.getTargetClassName<MainFragment>() -> {
//        true
//      }
//      else -> false
//    }
//  }

    private fun closeClickListener(api: ApiEnum, errorType: ApiError.ErrorType):
        DialogInterface.OnClickListener {
            return DialogInterface.OnClickListener { _, _ ->
                closeClickListener?.invoke(api, errorType)
            }
        }

    private fun retryClickListener(errorObj: ErrorObj, errorType: ApiError.ErrorType):
        DialogInterface.OnClickListener {
            return DialogInterface.OnClickListener { _, _ ->
                retryListener?.invoke(errorObj, errorType)
                    ?: errorObj.retry?.invoke()
            }
        }

    private fun forceClose(context: Context) {
        context.run {
//      Application.instance.logout()
//      val intent = Intent(this, LoginActivity::class.java)
//      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//      startActivity(intent)
        }
    }
}
