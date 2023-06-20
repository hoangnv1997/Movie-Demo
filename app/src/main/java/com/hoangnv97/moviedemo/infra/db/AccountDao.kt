package com.hoangnv97.moviedemo.infra.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hoangnv97.moviedemo.infra.db.entity.COLUMN_MAIL_ADDRESS
import com.hoangnv97.moviedemo.infra.db.entity.DbAccount
import com.hoangnv97.moviedemo.infra.db.entity.TABLE_ACCOUNT
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: DbAccount)

    @Update(entity = DbAccount::class)
    fun updateAccount(account: DbAccount)

    @Delete
    fun deleteAccount(account: DbAccount)

    @Transaction
    @Query("SELECT * FROM $TABLE_ACCOUNT")
    fun getAccountList(): Flow<List<DbAccount>>

    @Query("SELECT * FROM $TABLE_ACCOUNT WHERE $COLUMN_MAIL_ADDRESS LIKE :email")
    fun findByEmail(email: String): Flow<List<DbAccount>>
}
