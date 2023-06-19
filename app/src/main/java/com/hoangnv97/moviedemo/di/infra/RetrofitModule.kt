package com.hoangnv97.moviedemo.di.infra

import com.hoangnv97.moviedemo.BuildConfig
import com.hoangnv97.moviedemo.domain.api.MovieApi
import com.hoangnv97.moviedemo.infra.api.HeaderInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun okHttpBuilder(
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(headerInterceptor)
        builder.connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder
    }

    @Singleton
    @Provides
    fun provideMovieApi(
        builder: OkHttpClient.Builder,
        moshi: Moshi
    ): MovieApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(builder.build())
            .build().create(MovieApi::class.java)
    }

    const val SHORT_TIMEOUT = "short_timeout"

    @Singleton
    @Provides
    @Named(SHORT_TIMEOUT)
    fun provideMovieApiWithShortTimeout(
        builder: OkHttpClient.Builder,
        moshi: Moshi
    ): MovieApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(
                builder.build().newBuilder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build()
            )
            .build().create(MovieApi::class.java)
    }
}
