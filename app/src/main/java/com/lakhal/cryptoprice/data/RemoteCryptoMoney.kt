package com.lakhal.cryptoprice.data

import com.google.gson.annotations.SerializedName

const val DEFAULT_STRING = ""
const val DEFAULT_DOUBLE = 0.0

data class RemoteCryptoMoney(
    @SerializedName("Name")
    val name: String = DEFAULT_STRING,
    @SerializedName("Symbol")
    val symbol: String = DEFAULT_STRING,
    @SerializedName("Price")
    val price: Double = DEFAULT_DOUBLE,
    @SerializedName("DollarChange")
    val dollarChange: Double = DEFAULT_DOUBLE,
    @SerializedName("PercentChange")
    val percentageChange: Double = DEFAULT_DOUBLE,
    @SerializedName("DayLow")
    val dayLow: Double = DEFAULT_DOUBLE,
    @SerializedName("DayHigh")
    val dayHigh: Double = DEFAULT_DOUBLE,
    @SerializedName("YearLow")
    val yearLow: Double = DEFAULT_DOUBLE,
    @SerializedName("YearHigh")
    val yearHigh: Double = DEFAULT_DOUBLE,
    @SerializedName("MarketCap")
    val marketCap: Double = DEFAULT_DOUBLE,
    @SerializedName("Volume")
    val volume: Double = DEFAULT_DOUBLE,
    @SerializedName("DataCollected")
    val updatedAt: String = DEFAULT_STRING,
)
