package com.lakhal.cryptoprice.data.repositories.datasource

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.lakhal.cryptoprice.SettingsProto
import java.io.InputStream
import java.io.OutputStream

object SettingsProtoSerializer : Serializer<SettingsProto> {

    override val defaultValue: SettingsProto = SettingsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SettingsProto {
        try {
            return SettingsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read settings.", exception)
        }
    }

    override suspend fun writeTo(t: SettingsProto, output: OutputStream) = t.writeTo(output)
}
