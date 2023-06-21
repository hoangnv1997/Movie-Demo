package com.hoangnv97.moviedemo.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.databinding.NetworkStateItemBinding
import timber.log.Timber

class NetworkStateItemViewHolder(parent: ViewGroup, private val retryCallback: () -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
    ) {
    private val binding = NetworkStateItemBinding.bind(itemView)
    private val progressBar = binding.progressBar
    private val errorMsg = binding.errorMsg
    private val retry = binding.retryButton.also {
        it.setOnClickListener { retryCallback() }
    }

    fun bindTo(loadState: LoadState) {
        Timber.e(loadState.toString())
        progressBar.isGone = !(loadState is LoadState.Loading)
        retry.isGone = !(loadState is LoadState.Error)
        errorMsg.isGone = (loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMsg.text = (loadState as? LoadState.Error)?.error?.message
    }
}
