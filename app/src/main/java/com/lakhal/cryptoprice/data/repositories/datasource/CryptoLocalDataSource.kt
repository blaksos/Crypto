package com.lakhal.cryptoprice.data.repositories.datasource

import com.lakhal.cryptoprice.data.CryptoMoney

interface CryptoLocalDataSource {
    /**
     * Retrieve a list of all crypto money list.
     */
    suspend fun getCryptoMoneys(): List<CryptoMoney>

    /**
     * set The list of all crypto money list.
     */
    suspend fun setCryptoMoneyList(list: List<CryptoMoney>) : Boolean

    /**
     * Return the corresponding crypto money to the symbol passed if present, null if not.
     */
    suspend fun getCrypto(symbol: String): CryptoMoney?
}