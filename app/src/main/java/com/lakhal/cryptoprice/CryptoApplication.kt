package com.lakhal.cryptoprice

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltAndroidApp
class CryptoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MainScope().launch(Dispatchers.IO) {
            storeInitialCryptoList()
        }
    }

    private suspend fun storeInitialCryptoList() {
        // This is probably a complicated solution for what it is.
        // We can just store those static fields in the data source and deal with static list instead of DataStore and protobuf.
        val moneyListProto = cryptoMoneyListDataStore.data.first()
        if (moneyListProto.dataList.isEmpty()) {
            cryptoMoneyListDataStore.updateData { cryptoMoneyListProto ->
                cryptoMoneyListProto.toBuilder()
                    .addAllData(listOf(
                        CryptoMoneyProto.newBuilder().setName("Bitcoin").setSymbol("BTC").setLogoResId(R.drawable.btc).build(),
                        CryptoMoneyProto.newBuilder().setName("Bitcoin cash").setSymbol("BCH").setLogoResId(R.drawable.bch).build(),
                        CryptoMoneyProto.newBuilder().setName("Binance Coin").setSymbol("BNB").setLogoResId(R.drawable.bnb).build(),
                        CryptoMoneyProto.newBuilder().setName("Binance").setSymbol("BUSD").setLogoResId(R.drawable.busd).build(),
                        CryptoMoneyProto.newBuilder().setName("Ethereum").setSymbol("ETH").setLogoResId(R.drawable.eth).build(),
                        CryptoMoneyProto.newBuilder().setName("Dogecoin").setSymbol("DOGE").setLogoResId(R.drawable.doge).build(),
                        CryptoMoneyProto.newBuilder().setName("Ethereum Classic").setSymbol("ETC").setLogoResId(R.drawable.etc).build(),
                        CryptoMoneyProto.newBuilder().setName("Tether").setSymbol("USDT").setLogoResId(R.drawable.usdt).build(),
                        CryptoMoneyProto.newBuilder().setName("Tron").setSymbol("TRX").setLogoResId(R.drawable.trx).build(),
                        CryptoMoneyProto.newBuilder().setName("USD Coin").setSymbol("USDC").setLogoResId(R.drawable.usdc).build(),
                        CryptoMoneyProto.newBuilder().setName("XRP").setSymbol("XRP").setLogoResId(R.drawable.xrp).build(),
                        CryptoMoneyProto.newBuilder().setName("Cardano").setSymbol("ADA").setLogoResId(R.drawable.ada).build(),
                        CryptoMoneyProto.newBuilder().setName("Solana").setSymbol("SOL").setLogoResId(R.drawable.sol).build(),
                        CryptoMoneyProto.newBuilder().setName("Polkadot").setSymbol("DOT").setLogoResId(R.drawable.dot).build(),
                        CryptoMoneyProto.newBuilder().setName("Dai").setSymbol("DAI").setLogoResId(R.drawable.dai).build(),
                        CryptoMoneyProto.newBuilder().setName("Shiba Inu").setSymbol("SHIB").setLogoResId(R.drawable.shib).build(),
                        CryptoMoneyProto.newBuilder().setName("UNUS SED LEO").setSymbol("LEO").setLogoResId(R.drawable.leo).build(),
                        CryptoMoneyProto.newBuilder().setName("Avalanche").setSymbol("AVAX").setLogoResId(R.drawable.avax).build(),
                        CryptoMoneyProto.newBuilder().setName("Litecoin").setSymbol("LTC").setLogoResId(R.drawable.ltc).build(),
                        CryptoMoneyProto.newBuilder().setName("Stellar").setSymbol("XLM").setLogoResId(R.drawable.xlm).build(),
                    ))
                    .build()
            }
        }
    }
}