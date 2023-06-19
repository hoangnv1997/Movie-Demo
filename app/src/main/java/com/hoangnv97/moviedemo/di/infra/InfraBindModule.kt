package com.hoangnv97.moviedemo.di.infra

import com.hoangnv97.moviedemo.domain.api.ApiService
import com.hoangnv97.moviedemo.domain.appsettings.AppSettingsRepostory
import com.hoangnv97.moviedemo.infra.api.ApiServiceImpl
import com.hoangnv97.moviedemo.infra.appsettings.AppSettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InfraBindModule {

    @Binds
    abstract fun apiServise(service: ApiServiceImpl): ApiService

    @Binds
    @Singleton
    abstract fun appSettings(repo: AppSettingsRepositoryImpl): AppSettingsRepostory
}
