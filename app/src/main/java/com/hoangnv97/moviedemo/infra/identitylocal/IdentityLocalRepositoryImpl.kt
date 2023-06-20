package com.hoangnv97.moviedemo.infra.identitylocal

import com.hoangnv97.moviedemo.Util.Companion.createHash
import com.hoangnv97.moviedemo.domain.entity.Account
import com.hoangnv97.moviedemo.domain.identitylocal.IdentityLocalRepository
import com.hoangnv97.moviedemo.infra.db.AccountDao
import com.hoangnv97.moviedemo.infra.db.entity.DbAccount
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class IdentityLocalRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
) : IdentityLocalRepository {

    override suspend fun registerAccount(email: String, password: String) {
        val passwordHash = createHash(password)
        val account = DbAccount(email = email, password = passwordHash)
        accountDao.insertAccount(account)
    }

    override suspend fun isLoginSuccess(email: String, password: String): Boolean {
        val passwordHash = createHash(password)
        val account = accountDao.findByEmail(email).first()
            .firstOrNull { it.email == email && it.password == passwordHash }
        return account != null
    }

    override val accountList: Flow<List<Account>> =
        accountDao.getAccountList().map { accountList ->
            accountList.map {
                Account(
                    accountId = it.accountId,
                    mailAddress = it.email,
                    password = it.password
                )
            }
        }
}
