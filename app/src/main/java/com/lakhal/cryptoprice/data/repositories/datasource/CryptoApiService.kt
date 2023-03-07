package com.lakhal.cryptoprice.data.repositories.datasource

import com.lakhal.cryptoprice.data.RemoteCryptoMoney
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {

    @GET("/Crypto")
    suspend fun getCrypto(@Query("symbol") symbol: String): Response<RemoteCryptoMoney>
}