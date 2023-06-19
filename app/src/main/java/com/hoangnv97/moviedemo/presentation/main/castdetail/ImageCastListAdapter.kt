package com.hoangnv97.moviedemo.presentation.main.castdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoangnv97.moviedemo.databinding.ImageCastItemBinding
import com.hoangnv97.moviedemo.domain.entity.ImageCast

class ImageCastListAdapter(
    private val goToImageCast: (ImageCast) -> Unit
) : ListAdapter<ImageCast, ImageCastListAdapter.ViewHolder>(ImageCastDiffCallback()) {

    class ViewHolder(private val binding: ImageCastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageCast: ImageCast, goToImageCast: (ImageCast) -> Unit) {
            binding.imageCast = imageCast
            binding.goToImageCast = goToImageCast
            binding.executePendingBindings()
        }
    }

    private class ImageCastDiffCallback : DiffUtil.ItemCallback<ImageCast>() {
        override fun areItemsTheSame(oldItem: ImageCast, newItem: ImageCast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ImageCast, newItem: ImageCast): Boolean {
            return oldItem.filePath == newItem.filePath
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageCastItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), goToImageCast)
    }
}
