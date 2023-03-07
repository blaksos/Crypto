package com.lakhal.cryptoprice

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.lakhal.cryptoprice.data.repositories.datasource.CryptoMoneyListProtoSerializer
import com.lakhal.cryptoprice.data.repositories.datasource.SettingsProtoSerializer

val Context.cryptoMoneyListDataStore: DataStore<CryptoMoneyListProto> by dataStore(
    fileName = "CryptoMoneyProto.pb",
    serializer = CryptoMoneyListProtoSerializer
)

val Context.settingsDataStore: DataStore<SettingsProto> by dataStore(
    fileName = "CryptoMoneyProto.pb",
    serializer = SettingsProtoSerializer
)
