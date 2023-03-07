package com.lakhal.cryptoprice.ui.cryptodetails

import com.lakhal.cryptoprice.data.CryptoMoney

sealed class CryptoDetailsUiState {
    object LoadingState : CryptoDetailsUiState()
    data class ResultReceivedState(val cryptoMonies: CryptoMoney, val loading: Boolean) : CryptoDetailsUiState()
    data class ErrorState(val error: String) : CryptoDetailsUiState()
}