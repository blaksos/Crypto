package com.lakhal.cryptoprice.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.lakhal.cryptoprice.CryptoMoneyListProto
import com.lakhal.cryptoprice.cryptoMoneyListDataStore
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.data.CryptoMoneyMapper
import com.lakhal.cryptoprice.data.MapFrom
import com.lakhal.cryptoprice.data.RemoteCryptoMoney
import com.lakhal.cryptoprice.data.repositories.AltheiaHeadersInterceptor
import com.lakhal.cryptoprice.data.repositories.CryptoRepository
import com.lakhal.cryptoprice.data.repositories.CryptoRepositoryImpl
import com.lakhal.cryptoprice.data.repositories.datasource.ApiCryptoRemoteDataSource
import com.lakhal.cryptoprice.data.repositories.datasource.CryptoApiService
import com.lakhal.cryptoprice.data.repositories.datasource.CryptoLocalDataSource
import com.lakhal.cryptoprice.data.repositories.datasource.CryptoRemoteDataSource
import com.lakhal.cryptoprice.data.repositories.datasource.ProtoCryptoLocalSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.aletheiaapi.com/"

@Module
@InstallIn(SingletonComponent::class)
abstract class HitAppModule {

    @Binds
    abstract fun bindCryptoMapper(mapper: CryptoMoneyMapper): MapFrom<RemoteCryptoMoney?, CryptoMoney?>

    @Binds
    abstract fun bindRemoteDataSource(dataSource: ApiCryptoRemoteDataSource): CryptoRemoteDataSource

    companion object {

        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        @Singleton
        @Provides
        fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(AltheiaHeadersInterceptor())
                .build()

        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
        }

        @Singleton
        @Provides
        fun provideCryptoMoneyListDataSource(@ApplicationContext context: Context): DataStore<CryptoMoneyListProto> {
            return context.cryptoMoneyListDataStore
        }

        @Singleton
        @Provides
        fun provideCryptoApiService(retrofit: Retrofit): CryptoApiService = retrofit.create(CryptoApiService::class.java)

        @Singleton
        @Provides
        fun provideCryptoLocalDataSource(listDataSource: DataStore<CryptoMoneyListProto>): CryptoLocalDataSource {
            return ProtoCryptoLocalSource(listDataSource)
        }

        @Singleton
        @Provides
        fun provideCryptoRepository(
            localDataSource: CryptoLocalDataSource,
            remoteDataSource: CryptoRemoteDataSource,
            cryptoMoneyMapper: MapFrom<RemoteCryptoMoney?, CryptoMoney?>,
        ): CryptoRepository {
            return CryptoRepositoryImpl(Dispatchers.IO, localDataSource, remoteDataSource, cryptoMoneyMapper)
        }

        @Singleton
        @Provides
        fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}
