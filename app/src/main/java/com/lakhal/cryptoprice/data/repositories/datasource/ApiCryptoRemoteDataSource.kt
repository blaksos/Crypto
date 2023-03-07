package com.lakhal.cryptoprice.data.repositories.datasource

import com.lakhal.cryptoprice.data.RemoteCryptoMoney
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ApiCryptoRemoteDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val api: CryptoApiService,
) : CryptoRemoteDataSource {

    override suspend fun getCrypto(symbol: String) : RemoteCryptoMoney = withContext(ioDispatcher) {
        val crypto = api.getCrypto(symbol).body()
        crypto ?: RemoteCryptoMoney()
    }
}