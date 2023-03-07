package com.lakhal.cryptoprice.data

import javax.inject.Inject

/**
 * Mapper class to map a RemoteCryptoMoney received from the APiService to CryptoMoney.
 */
class CryptoMoneyMapper @Inject constructor() : MapFrom<@JvmSuppressWildcards RemoteCryptoMoney?, @JvmSuppressWildcards CryptoMoney?> {
    override fun mapFrom(input: RemoteCryptoMoney?): CryptoMoney? {
        input?.let {
            return CryptoMoney(
                name = it.name,
                symbol = it.symbol,
                price = it.price,
                dollarChange = it.dollarChange,
                dayHigh = it.dayHigh,
                dayLow = it.dayLow,
                marketCap = it.marketCap,
                percentageChange = it.percentageChange,
                volume = it.volume,
                updatedAt = it.updatedAt,
            )
        }

        return null
    }
}