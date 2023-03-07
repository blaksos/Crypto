package com.lakhal.cryptoprice.data

interface MapFrom<In, Out> {

    fun mapFrom(input: In): Out
}
