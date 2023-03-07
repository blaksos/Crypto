package com.lakhal.cryptoprice.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CryptoMoney(
    val name: String,
    val symbol: String,
    var logoResId: Int? = null,
    val price: Double? = null,
    val dollarChange: Double? = null,
    val percentageChange: Double? = null,
    val dayLow: Double? = null,
    val dayHigh: Double? = null,
    val yearLow: Double? = null,
    val yearHigh: Double? = null,
    val marketCap: Double? = null,
    val volume: Double? = null,
    val updatedAt: String? = null,
) : Parcelable