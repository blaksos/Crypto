package com.lakhal.cryptoprice.ui.cryptoList

import com.google.common.truth.Truth.assertThat
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.data.repositories.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class CryptoListViewModelTest {

    @Mock
    lateinit var mockCryptoRepository: CryptoRepository

    private val testCoroutineDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: CryptoListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun cryptoListViewModel_onCreated_ShouldReceiveEmptyList() = runTest {
        // Given
        whenever(mockCryptoRepository.getAllCryptoMoneys()).thenReturn(flowOf(emptyList()))
        viewModel = CryptoListViewModel(mockCryptoRepository, testCoroutineDispatcher)

        // When

        // Then
        verify(mockCryptoRepository).getAllCryptoMoneys()
        assertThat(viewModel.uiState.first()).isEqualTo(CryptoListUiState.ResultReceivedState(emptyList()))
    }

    @Test
    fun cryptoListViewModel_onCreated_ShouldEmitCryptoList() = runTest {
        // Given
        val element = CryptoMoney(
            name = "test-name",
            symbol = "TEST",
        )

        whenever(mockCryptoRepository.getAllCryptoMoneys()).thenReturn(flowOf(listOf(element)))
        viewModel = CryptoListViewModel(mockCryptoRepository, testCoroutineDispatcher)

        // When

        // Then
        verify(mockCryptoRepository).getAllCryptoMoneys()
        assertThat(viewModel.uiState.first()).isEqualTo(CryptoListUiState.ResultReceivedState(listOf(element)))
    }

    @Test
    fun cryptoListViewModel_whenRepositoryThrowException_ShouldEmitErrorState() = runTest {
        // Given
        val exception = java.lang.IllegalArgumentException("Exception from test")
        whenever(mockCryptoRepository.getAllCryptoMoneys()).thenThrow(exception)
        viewModel = CryptoListViewModel(mockCryptoRepository, testCoroutineDispatcher)

        // When

        // Then
        verify(mockCryptoRepository).getAllCryptoMoneys()
        assertThat(viewModel.uiState.first()).isEqualTo(CryptoListUiState.ErrorState(exception.localizedMessage))
    }

    @Test
    fun cryptoListViewModel_whenCryptoMoneySelected_ShouldEmitNavigation() = runTest {
        // Given
        val money = CryptoMoney(
            name = "test-name",
            symbol = "TEST",
        )

        val exception = java.lang.IllegalArgumentException("Exception from test")
        whenever(mockCryptoRepository.getAllCryptoMoneys()).thenThrow(exception)
        viewModel = CryptoListViewModel(mockCryptoRepository, testCoroutineDispatcher)

        // When
        viewModel.onCryptoMoneySelected(money)

        // Then
        assertThat(viewModel.uiState.first()).isEqualTo(CryptoListUiState.NavigateToDetailsFragment(money))
    }
}