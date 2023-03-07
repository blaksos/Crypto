package com.lakhal.cryptoprice.ui.cryptoList

import com.lakhal.cryptoprice.data.CryptoMoney

sealed class CryptoListUiState {
    object LoadingState : CryptoListUiState()
    data class ResultReceivedState(val cryptoMonies: List<CryptoMoney>) : CryptoListUiState()
    data class ErrorState(val error: String) : CryptoListUiState()
    data class NavigateToDetailsFragment(val cryptoMoney: CryptoMoney) : CryptoListUiState()
}