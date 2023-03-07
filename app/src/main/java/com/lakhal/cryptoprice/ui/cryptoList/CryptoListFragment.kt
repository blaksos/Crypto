package com.lakhal.cryptoprice.ui.cryptoList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.databinding.FragmentCryptoListBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "CryptoListFragment"

@AndroidEntryPoint
class CryptoListFragment : Fragment() {

    private var _binding: FragmentCryptoListBinding? = null

    private val binding get() = _binding!!
    private val viewModel: CryptoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCryptoListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { data ->
                when (data) {
                    is CryptoListUiState.ErrorState -> displayError(data.error)
                    CryptoListUiState.LoadingState -> displayLoading()
                    is CryptoListUiState.ResultReceivedState -> displayResult(data.cryptoMonies)
                    is CryptoListUiState.NavigateToDetailsFragment -> displayDetailsFragment(data.cryptoMoney)
                }
            }
        }

        viewModel.fetchCryptoList()
        return root
    }

    private fun displayDetailsFragment(cryptoMoney: CryptoMoney) {
        Log.d(TAG, "displayDetailsFragment() called with: symbol = $cryptoMoney")

        val navDirections = CryptoListFragmentDirections.actionNavigationHomeToNavigationCryptoDetails(cryptoArgument = cryptoMoney)

        findNavController(this).navigate(navDirections)
    }

    private fun displayLoading() {
        Log.d(TAG, "displayLoading() called")
    }

    private fun displayError(error: String) {
        Log.d(TAG, "displayError() called with: error = $error")
    }

    private fun displayResult(result: List<CryptoMoney>) {
        Log.d(TAG, "displayResult() called with: result = $result")
        binding.cryptoListRecyclerView.adapter = CryptoMoneyAdapter(result) {
            viewModel.onCryptoMoneySelected(it)
        }

        binding.cryptoListRecyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}