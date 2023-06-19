package com.hoangnv97.moviedemo.presentation.main.castdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoangnv97.moviedemo.databinding.RelatedMovieItemBinding
import com.hoangnv97.moviedemo.domain.entity.RelatedMovie

class RelatedMovieListAdapter(
    private val goToDetail: (RelatedMovie) -> Unit
) : ListAdapter<RelatedMovie, RelatedMovieListAdapter.ViewHolder>(CreditDiffCallback()) {

    class ViewHolder(private val binding: RelatedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(relatedMovie: RelatedMovie, goToDetail: (RelatedMovie) -> Unit) {
            binding.relatedMovie = relatedMovie
            binding.goToDetail = goToDetail
            binding.executePendingBindings()
        }
    }

    private class CreditDiffCallback : DiffUtil.ItemCallback<RelatedMovie>() {
        override fun areItemsTheSame(oldItem: RelatedMovie, newItem: RelatedMovie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RelatedMovie, newItem: RelatedMovie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RelatedMovieItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), goToDetail)
    }
}
