package com.lakhal.cryptoprice.ui.cryptodetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.lakhal.cryptoprice.R
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.databinding.FragmentCryptoDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "CryptoDetailsFragment"

@AndroidEntryPoint
class CryptoDetailsFragment : Fragment() {

    private var _binding: FragmentCryptoDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CryptoDetailsFragmentViewModel by viewModels()
    private val safeArgs: CryptoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState")

        _binding = FragmentCryptoDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.uiState.observe(viewLifecycleOwner) { data ->
            when (data) {
                is CryptoDetailsUiState.ErrorState -> displayError(data.error)
                CryptoDetailsUiState.LoadingState -> displayLoading()
                is CryptoDetailsUiState.ResultReceivedState -> {
                    displayResult(data.cryptoMonies)
                    if (data.loading) {
                        displayLoading()
                    } else {
                        hideLoading()
                    }
                }
            }
        }

        viewModel.loadCryptoDetails(safeArgs.cryptoArgument)

        return root
    }

    private fun displayResult(money: CryptoMoney) {
        val unknownString = getString(R.string.unknown)
        val description = getString(
            R.string.crypto_money_description,
            money.price.toString(),
            money.dollarChange?.toString() ?: unknownString,
            money.percentageChange?.toString() ?: unknownString,
            money.dayLow?.toString() ?: unknownString,
            money.dayHigh?.toString() ?: unknownString,
            money.yearLow?.toString() ?: unknownString,
            money.yearHigh?.toString() ?: unknownString,
            money.marketCap?.toString() ?: unknownString,
            money.volume?.toString() ?: unknownString,
            money.updatedAt ?: unknownString, // This date should be formatted to human readable date.
        )

        binding.symbolTextView.text = money.symbol

        // We don't want to update the name from server as it may different with the one we have.
        // Example: Binance coin return as BNB from the Api
        if (binding.nameTextView.text.isNullOrBlank()) {
            binding.nameTextView.text = money.name
        }
        binding.resultTextView.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)

        money.logoResId?.let {
            // We need to check for valid resource Id here as we will receive invalid values when having it from the API.
            if (it > 0) {
                binding.logoImageView.setImageResource(it)
            }
        }
    }

    private fun displayLoading() {
        Log.d(TAG, "displayLoading() called")

        binding.loader.visibility = View.VISIBLE
        binding.resultTextView.visibility = View.GONE
    }

    private fun hideLoading() {
        Log.d(TAG, "hideLoading() called")

        binding.loader.visibility = View.GONE
        binding.resultTextView.visibility = View.VISIBLE
    }

    private fun displayError(error: String) {
        Log.d(TAG, "displayError() called with: error = $error")

        binding.loader.visibility = View.GONE
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = getString(R.string.fragment_details_error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}