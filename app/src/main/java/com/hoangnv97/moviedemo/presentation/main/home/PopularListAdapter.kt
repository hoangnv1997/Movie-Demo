package com.hoangnv97.moviedemo.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hoangnv97.moviedemo.databinding.MovieItemBinding
import com.hoangnv97.moviedemo.domain.entity.Movie

class PopularListAdapter(
    private val itemFirstCallBack: (Movie) -> Unit,
    private val goToDetail: (Movie) -> Unit,
) : PagingDataAdapter<Movie, PopularListAdapter.ViewHolder>(popularDiffUtilCallBack) {

    companion object {
        val popularDiffUtilCallBack = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    class ViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie?, goToDetail: (Movie) -> Unit) {
            binding.movie = item
            binding.goToDetail = goToDetail
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (position == 0) {
            item?.let(itemFirstCallBack)
        }
        holder.bind(item, goToDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}
