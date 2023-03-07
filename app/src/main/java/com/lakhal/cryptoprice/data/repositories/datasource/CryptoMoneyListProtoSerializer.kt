package com.lakhal.cryptoprice.data.repositories.datasource

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.lakhal.cryptoprice.CryptoMoneyListProto
import java.io.InputStream
import java.io.OutputStream

object CryptoMoneyListProtoSerializer : Serializer<CryptoMoneyListProto> {

    override val defaultValue: CryptoMoneyListProto = CryptoMoneyListProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CryptoMoneyListProto {
        try {
            return CryptoMoneyListProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CryptoMoneyListProto, output: OutputStream) = t.writeTo(output)
}