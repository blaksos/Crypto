package com.lakhal.cryptoprice.data.repositories

class PhoneDateTimeRepository : DateTimeRepository {

    override fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}