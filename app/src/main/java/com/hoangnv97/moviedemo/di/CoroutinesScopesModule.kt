package com.hoangnv97.moviedemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Qualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(ActivityRetainedComponent::class)
object CoroutinesScopesModule {
    @DefaultCoroutineScope
    @Provides
    fun default(@DefaultDispatcher defaultDispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(SupervisorJob() + defaultDispatcher)

    @IOCoroutineScope
    @Provides
    fun io(@IODispatcher ioDispatcher: CoroutineDispatcher): CoroutineScope = CoroutineScope(
        SupervisorJob() + ioDispatcher
    )
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultCoroutineScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IOCoroutineScope
