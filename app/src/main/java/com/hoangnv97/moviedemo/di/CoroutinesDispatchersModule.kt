package com.hoangnv97.moviedemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesDispatchersModule {
    @MainDispatcher
    @Provides
    fun main(): CoroutineDispatcher = Dispatchers.Main

    @IODispatcher
    @Provides
    fun io(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun default(): CoroutineDispatcher = Dispatchers.Default
}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class IODispatcher

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher
