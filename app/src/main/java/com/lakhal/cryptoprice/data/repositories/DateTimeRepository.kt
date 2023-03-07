package com.lakhal.cryptoprice.data.repositories

interface DateTimeRepository {

    /**
     * Return the current system time in milliseconds.
     */
    fun currentTimeMillis(): Long
}