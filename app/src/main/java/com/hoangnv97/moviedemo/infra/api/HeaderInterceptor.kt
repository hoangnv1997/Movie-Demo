package com.hoangnv97.moviedemo.infra.api

import com.hoangnv97.moviedemo.BuildConfig
import com.hoangnv97.moviedemo.domain.appsettings.AppSettingsRepostory
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor @Inject constructor(
    private val appSettingsRepostory: AppSettingsRepostory,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val oldRequest = chain.request()
//        val isUs = oldRequest.header("isUs") == "true"
        val url = oldRequest.url.newBuilder()
//            .addQueryParameter("language", if (isUs) "en-US" else "vi-VN")
            .addQueryParameter("language", "en-US")
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(
            chain.request().run {
                newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("accept", "application/json")
//                    .removeHeader("isUs")
//                    .apply {
//                        appSettingsRepostory.getAccessToken()?.also {
//                            if (it.isNotEmpty()) {
//                                addHeader("authorization", "$it")
//                            }
//                        }
//                    }
                    .url(url)
                    .method(method, body)
                    .build()
            }
        )
    }
}
