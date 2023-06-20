package com.hoangnv97.moviedemo.di.infra

import android.content.Context
import androidx.room.Room
import com.hoangnv97.moviedemo.infra.db.MovieDemoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDemoDb::class.java, "moviedemo.db")
//            .addMigrations(MOVIE_MIGRATION_1_2)
            .build()

    @Singleton
    @Provides
    fun account(db: MovieDemoDb) = db.account()
}
