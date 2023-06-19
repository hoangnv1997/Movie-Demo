package com.hoangnv97.moviedemo.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hoangnv97.moviedemo.databinding.MovieItemBinding
import com.hoangnv97.moviedemo.databinding.MovieItemGridBinding
import com.hoangnv97.moviedemo.domain.entity.Movie

class MoviePagingAdapter(private val goToDetail: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MoviePagingAdapter.ViewHolder>(movieDiffUtilCallBack) {

    companion object {
        val movieDiffUtilCallBack = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    private var isMovieItemGrid = false

    class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie?, goToDetail: (Movie) -> Unit) {
            when (binding) {
                is MovieItemBinding -> {
                    binding.movie = item
                    binding.goToDetail = goToDetail
                    binding.executePendingBindings()
                }
                is MovieItemGridBinding -> {
                    binding.movie = item
                    binding.goToDetail = goToDetail
                    binding.executePendingBindings()
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        isMovieItemGrid = recyclerView.layoutManager is GridLayoutManager
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), goToDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = if (isMovieItemGrid) {
            MovieItemGridBinding.inflate(layoutInflater, parent, false)
        } else {
            MovieItemBinding.inflate(layoutInflater, parent, false)
        }
        return ViewHolder(binding)
    }
}
