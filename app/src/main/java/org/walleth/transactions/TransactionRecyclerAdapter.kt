package org.AimEx.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import org.AimEx.R
import org.AimEx.data.AppDatabase
import org.AimEx.data.config.Settings
import org.AimEx.data.exchangerate.ExchangeRateProvider
import org.AimEx.chains.ChainInfoProvider
import org.AimEx.data.transactions.TransactionEntity

enum class TransactionAdapterDirection {
    INCOMING, OUTGOING
}

class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionEntity>() {

    override fun areItemsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
        return oldItem.hash == newItem.hash
    }

    override fun areContentsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
        return oldItem == newItem
    }
}


class TransactionRecyclerAdapter(val appDatabase: AppDatabase,
                                 private val direction: TransactionAdapterDirection,
                                 val chainInfoProvider: ChainInfoProvider,
                                 private val exchangeRateProvider: ExchangeRateProvider,
                                 val settings: Settings
) : PagedListAdapter<TransactionEntity, TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) = holder.bind(getItem(position), appDatabase)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(itemView, direction, chainInfoProvider, exchangeRateProvider, settings)
    }

}