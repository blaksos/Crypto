package com.lakhal.cryptoprice.ui.cryptoList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lakhal.cryptoprice.data.CryptoMoney
import com.lakhal.cryptoprice.databinding.CryptoListItemBinding

class CryptoMoneyAdapter(
    private val dataSet: List<CryptoMoney>,
    private val onItemClicked: (CryptoMoney) -> Unit,
) : RecyclerView.Adapter<CryptoMoneyAdapter.CryptoMoneyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoMoneyViewHolder {
        val binding = CryptoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoMoneyViewHolder(binding) {
            onItemClicked(dataSet[it])
        }
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CryptoMoneyViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    class CryptoMoneyViewHolder(binding: CryptoListItemBinding, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        private var symbolTextView: TextView
        private var nameTextView: TextView
        private var logoImageView: ImageView

        fun bind(cryptoMoney: CryptoMoney) {
            nameTextView.text = cryptoMoney.name
            symbolTextView.text = cryptoMoney.symbol
            cryptoMoney.logoResId?.let {
                logoImageView.setImageResource(it)
            }
        }

        init {
            nameTextView = binding.nameTextView
            symbolTextView = binding.symbolTextView
            logoImageView = binding.logoImageView

            itemView.setOnClickListener {
                // this will be called only once.
                onItemClicked(adapterPosition)
            }
        }
    }
}