package com.lakhal.cryptoprice.ui.cryptodetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.data.ResultData
import com.lakhal.cryptoprice.data.asResult
import com.lakhal.cryptoprice.data.repositories.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CryptoDetailsFragmentViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData<CryptoDetailsUiState>()

    val uiState: LiveData<CryptoDetailsUiState> get() = _uiState

    fun loadCryptoDetails(cryptoMoney: CryptoMoney) {
        if (_uiState.value != null) {
            return
        }

        viewModelScope.launch {
            _uiState.postValue(CryptoDetailsUiState.ResultReceivedState(cryptoMoney, loading = true))

            runCatching {
                cryptoRepository.getCryptoMoneyBySymbol(symbol = cryptoMoney.symbol)
            }.onSuccess {
                it.asResult().collect { result ->
                    val newState = when (result) {
                        ResultData.Loading -> CryptoDetailsUiState.LoadingState
                        is ResultData.Success -> {
                            val data = result.data.apply {
                                this?.logoResId = cryptoMoney.logoResId
                            }

                            if (data != null) {
                                CryptoDetailsUiState.ResultReceivedState(data, false)
                            } else {
                                CryptoDetailsUiState.ErrorState("Unknown error occurred.")
                            }
                        }
                        is ResultData.Error -> CryptoDetailsUiState.ErrorState(result.exception?.localizedMessage ?: "Unknown error occurred.")
                    }
                    _uiState.postValue(newState)
                }
            }.onFailure {
                _uiState.postValue(CryptoDetailsUiState.ErrorState(it.localizedMessage ?: "Unknown error occurred."))
            }

        }
    }
}