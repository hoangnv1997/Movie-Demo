package com.hoangnv97.moviedemo.domain.identitylocal

import com.hoangnv97.moviedemo.domain.entity.Account
import kotlinx.coroutines.flow.Flow

interface IdentityLocalRepository {

    suspend fun registerAccount(email: String, password: String)
    suspend fun isLoginSuccess(email: String, password: String): Boolean
    val accountList: Flow<List<Account>>
}
