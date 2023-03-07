package com.lakhal.cryptoprice.data.repositories

import com.google.common.truth.Truth.assertThat
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.data.RemoteCryptoMoney
import com.lakhal.cryptoprice.data.repositories.datasource.CryptoApiService
import com.lakhal.cryptoprice.data.repositories.datasource.CryptoLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CryptoRepositoryImplTest {

    lateinit var cryptoRepository: CryptoRepository

    @Mock
    lateinit var mockCryptoLocalDataSource: CryptoLocalDataSource

    @Mock
    lateinit var mockCryptoApiService: CryptoApiService

    private val testCoroutineDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        cryptoRepository = CryptoRepositoryImpl(
            testCoroutineDispatcher,
            mockCryptoLocalDataSource,
            mockCryptoApiService
        )
    }

    @Test
    fun cryptoRepository_getCryptoMoneys_returnEmptyList() = runTest {
        // Given
        whenever(mockCryptoLocalDataSource.getCryptoMoneys()).thenReturn(emptyList())

        // When
        val result = cryptoRepository.getAllCryptoMoneys()

        // Then
        assertThat(result.first()).isEmpty()
    }

    @Test
    fun cryptoRepository_getCryptoMoneys_returnNonEmptyList() = runTest {
        // Given
        val money = CryptoMoney(
            name = "test",
            symbol = "TEST"
        )
        whenever(mockCryptoLocalDataSource.getCryptoMoneys()).thenReturn(listOf(money))

        // When
        val result = cryptoRepository.getAllCryptoMoneys().first()

        // Then
        assertThat(result).isNotEmpty()
        assertThat(result.size).isEqualTo(1)
        assertThat(result.first()).isEqualTo(money)
    }

    @Test
    fun cryptoRepository_getCryptoMoneyBySymbol_notAvailableSymbol() = runTest {
        // Given
        val money = CryptoMoney(
            name = "test",
            symbol = "TEST"
        )
        whenever(mockCryptoApiService.getCrypto(anyString())).thenReturn(Response.success(null))

        // When
        val result = cryptoRepository.getCryptoMoneyBySymbol("TEST").first()

        // Then
        assertThat(result).isNull()
    }

    @Test
    fun cryptoRepository_getCryptoMoneyBySymbol_availableSymbol() = runTest {
        // Given
        val remoteMoney = RemoteCryptoMoney(
            name = "test",
            symbol = "TEST",
            price = 23.45,
        )
        whenever(mockCryptoApiService.getCrypto(anyString())).thenReturn(Response.success(remoteMoney))

        // When
        val result = cryptoRepository.getCryptoMoneyBySymbol("TEST").first()

        // Then
        val expected = CryptoMoney(
            name = "test",
            symbol = "TEST",
            price = 23.45,
        )
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(expected)
    }
}