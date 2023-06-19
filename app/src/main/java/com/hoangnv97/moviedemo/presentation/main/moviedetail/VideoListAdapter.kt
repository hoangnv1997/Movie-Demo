package com.hoangnv97.moviedemo.presentation.main.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoangnv97.moviedemo.databinding.VideoItemBinding
import com.hoangnv97.moviedemo.domain.entity.Video

class VideoListAdapter(
    private val goToWatchVideo: (Video) -> Unit
) : ListAdapter<Video, VideoListAdapter.ViewHolder>(videoDiffUtilCallBack) {

    companion object {
        val videoDiffUtilCallBack = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    class ViewHolder(private val binding: VideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video, goToWatchVideo: (Video) -> Unit) {
            binding.video = video
            binding.goToWatchVideo = goToWatchVideo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VideoItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), goToWatchVideo)
    }
}
