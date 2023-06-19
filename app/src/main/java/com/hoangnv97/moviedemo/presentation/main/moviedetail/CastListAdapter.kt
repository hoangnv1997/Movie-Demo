package com.hoangnv97.moviedemo.presentation.main.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoangnv97.moviedemo.databinding.CastItemBinding
import com.hoangnv97.moviedemo.domain.entity.Cast

class CastListAdapter(
    private val goToCast: (Cast) -> Unit
) : ListAdapter<Cast, CastListAdapter.ViewHolder>(CastDiffCallback()) {

    class ViewHolder(private val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast, goToCast: (Cast) -> Unit) {
            binding.cast = cast
            binding.goToCast = goToCast
            binding.executePendingBindings()
        }
    }

    private class CastDiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CastItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), goToCast)
    }
}
