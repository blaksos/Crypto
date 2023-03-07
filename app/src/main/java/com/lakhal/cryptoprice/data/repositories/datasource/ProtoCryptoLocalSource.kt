package com.lakhal.cryptoprice.data.repositories.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import com.lakhal.cryptoprice.CryptoMoneyListProto
import com.lakhal.cryptoprice.CryptoMoneyProto
import com.lakhal.cryptoprice.data.CryptoMoney
import javax.inject.Inject
import kotlinx.coroutines.flow.first

private const val TAG = "ProtoCryptoLocalSource"

class ProtoCryptoLocalSource @Inject constructor(
    private val dataStore: DataStore<CryptoMoneyListProto>,
) : CryptoLocalDataSource {

    override suspend fun getCryptoMoneys(): List<CryptoMoney> {
        val moneyListProto = dataStore.data.first()

        Log.d(TAG, "getCryptoMoneys() returned: ${moneyListProto.dataList.size}")

        return moneyListProto.dataList.map {
            CryptoMoney(name = it.name, symbol = it.symbol, logoResId = it.logoResId)
        }
    }

    override suspend fun setCryptoMoneyList(list: List<CryptoMoney>): Boolean {
        Log.d(TAG, "setCryptoMoneyList() called with: list = $list")

        dataStore.updateData { cryptoMoneyListProto ->
            val listProto = list.map { cryptoMoney ->
                CryptoMoneyProto.newBuilder().setName(cryptoMoney.name).setSymbol(cryptoMoney.symbol).setLogoResId(cryptoMoney.logoResId ?: -1)
                    .build()
            }
            cryptoMoneyListProto.toBuilder()
                .addAllData(listProto)
                .build()
        }
        return true
    }

    override suspend fun getCrypto(symbol: String): CryptoMoney? {
        val moneyListProto = dataStore.data.first()
        val cryptoMoneyProto = moneyListProto.dataList.firstOrNull {
            it.symbol == symbol
        }

        cryptoMoneyProto?.let {
            return CryptoMoney(name = it.name, symbol = it.symbol, logoResId = it.logoResId)
        }

        return null
    }
}