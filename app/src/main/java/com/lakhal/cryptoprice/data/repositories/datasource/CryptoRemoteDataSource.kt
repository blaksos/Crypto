package com.lakhal.cryptoprice.data.repositories.datasource

import com.lakhal.cryptoprice.data.RemoteCryptoMoney

interface CryptoRemoteDataSource {

    suspend fun getCrypto(symbol: String): RemoteCryptoMoney
}