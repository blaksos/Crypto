package com.lakhal.cryptoprice.data.repositories

import com.lakhal.cryptoprice.data.CryptoMoney
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    /**
     * Retrieve all available crypto currencies list.
     */
    suspend fun getAllCryptoMoneys(): Flow<List<CryptoMoney>>

    /**
     * Retrieve the crypto currency for the given symbol.
     */
    suspend fun getCryptoMoneyBySymbol(symbol: String) : Flow<CryptoMoney?>
}