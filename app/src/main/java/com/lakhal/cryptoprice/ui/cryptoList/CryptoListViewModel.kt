package com.lakhal.cryptoprice.ui.cryptoList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.data.ResultData
import com.lakhal.cryptoprice.data.asResult
import com.lakhal.cryptoprice.data.repositories.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private var _uiState = Channel<CryptoListUiState>(capacity = CONFLATED)
    val uiState: Flow<CryptoListUiState> = _uiState.receiveAsFlow()

    fun fetchCryptoList() {
        viewModelScope.launch(dispatcher) {
            kotlin.runCatching {
                cryptoRepository.getAllCryptoMoneys()
            }
                .onSuccess {
                    it.asResult()
                        .collect { result ->
                            val newState = when (result) {
                                ResultData.Loading -> CryptoListUiState.LoadingState
                                is ResultData.Success -> CryptoListUiState.ResultReceivedState(result.data)
                                is ResultData.Error -> CryptoListUiState.ErrorState(result.exception?.localizedMessage ?: "Unknown error occurred.")
                            }

                            _uiState.send(newState)
                        }
                }
                .onFailure {
                    _uiState.send(CryptoListUiState.ErrorState(it.localizedMessage ?: "Unknown error occurred."))
                }

        }
    }

    fun onCryptoMoneySelected(cryptoMoney: CryptoMoney) {
        Log.d(TAG, "onCryptoMoneySelected() called with: cryptoMoney = $cryptoMoney")
        viewModelScope.launch(dispatcher) {
            _uiState.send(CryptoListUiState.NavigateToDetailsFragment(cryptoMoney))
        }
    }
}