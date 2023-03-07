package com.lakhal.cryptoprice.data.repositories

import android.util.Log
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.data.MapFrom
import com.lakhal.cryptoprice.data.RemoteCryptoMoney
import com.lakhal.cryptoprice.data.repositories.datasource.CryptoLocalDataSource
import com.lakhal.cryptoprice.data.repositories.datasource.CryptoRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

private const val TAG = "CryptoRepositoryImpl"

@Singleton
class CryptoRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val localDataSource: CryptoLocalDataSource,
    private val remoteDataSource: CryptoRemoteDataSource,
    private val cryptoMoneyMapper: MapFrom<RemoteCryptoMoney?, CryptoMoney?>
) : CryptoRepository {

    override suspend fun getAllCryptoMoneys(): Flow<List<CryptoMoney>> = withContext(ioDispatcher) {
        Log.d(TAG, "getAllCryptoMoneys() called")
        flowOf(localDataSource.getCryptoMoneys())
    }

    override suspend fun getCryptoMoneyBySymbol(symbol: String): Flow<CryptoMoney?> = withContext(ioDispatcher) {
        Log.d(TAG, "getCryptoMoneyBySymbol() called")
        val remoteCryptoMoney = remoteDataSource.getCrypto(symbol)
        flowOf(cryptoMoneyMapper.mapFrom(remoteCryptoMoney))
    }
}