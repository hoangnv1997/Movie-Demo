package com.hoangnv97.moviedemo.presentation.main.discovertvshowlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.hoangnv97.moviedemo.databinding.TvShowItemBinding
import com.hoangnv97.moviedemo.databinding.TvShowItemGridBinding
import com.hoangnv97.moviedemo.domain.entity.TVShow

class DiscoverTVShowListAdapter(
    private val goToTVShowDetail: (TVShow) -> Unit
) : PagingDataAdapter<TVShow, DiscoverTVShowListAdapter.ViewHolder>(tvShowsDiffUtilCallBack) {

    private var isItemGrid: Boolean = false

    class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TVShow?, goToTvShowDetail: (TVShow) -> Unit) {
            when (binding) {
                is TvShowItemGridBinding -> {
                    binding.tvShow = item
                    binding.goToTVShowDetail = goToTvShowDetail
                    binding.executePendingBindings()
                }
                is TvShowItemBinding -> {
                    binding.tvShow = item
                    binding.goToTVShowDetail = goToTvShowDetail
                    binding.executePendingBindings()
                }
            }
        }
    }

    companion object {
        val tvShowsDiffUtilCallBack = object : DiffUtil.ItemCallback<TVShow>() {
            override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        isItemGrid = recyclerView.layoutManager is GridLayoutManager
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), goToTVShowDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = if (isItemGrid) {
            TvShowItemGridBinding.inflate(layoutInflater, parent, false)
        } else {
            TvShowItemBinding.inflate(layoutInflater, parent, false)
        }
        return ViewHolder(binding)
    }
}
