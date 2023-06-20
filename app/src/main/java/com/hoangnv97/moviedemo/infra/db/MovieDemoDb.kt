package com.hoangnv97.moviedemo.infra.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hoangnv97.moviedemo.infra.db.entity.DbAccount

@Database(version = 1, entities = [DbAccount::class])
abstract class MovieDemoDb : RoomDatabase() {
    abstract fun account(): AccountDao
}

// val MOVIE_MIGRATION_1_2 = object : Migration(1,2){
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE $TABLE_ACCOUNT ADD $COLUMN_NAME TEXT NOT NULL ''")
//    }
// }
