package com.lakhal.cryptoprice.data.repositories

import com.lakhal.cryptoprice.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Custom interceptor to satisfy Altheia api request required headers.
 */
class AltheiaHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            // This api key shouldn't be here, instead we should store it in a more safe manner like obfuscate with dexguard,
            // or encrypt it.
            .addHeader("key", BuildConfig.ALTHEIA_API_KEY)
            .addHeader("Accept-Version", "2")
            .build()
        return chain.proceed(request)
    }
}
