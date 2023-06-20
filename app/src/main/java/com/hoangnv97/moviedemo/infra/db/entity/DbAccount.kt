package com.hoangnv97.moviedemo.infra.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_ACCOUNT = "account"

const val COLUMN_ID = "id"
const val COLUMN_MAIL_ADDRESS = "mail_address"
const val COLUMN_PASSWORD = "password"
const val COLUMN_NAME = "name"

@Entity(tableName = TABLE_ACCOUNT)
data class DbAccount(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID) val accountId: Int = 0,
    @ColumnInfo(name = COLUMN_MAIL_ADDRESS) val email: String,
    @ColumnInfo(name = COLUMN_PASSWORD) val password: String
)
