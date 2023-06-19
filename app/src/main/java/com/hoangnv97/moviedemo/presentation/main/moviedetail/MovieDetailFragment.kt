package com.hoangnv97.moviedemo.presentation.main.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.databinding.FragmentMovieDetailBinding
import com.hoangnv97.moviedemo.domain.api.TheMovieConstants
import com.hoangnv97.moviedemo.domain.entity.Cast
import com.hoangnv97.moviedemo.domain.entity.Video
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override val viewModel: MovieDetailViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_movie_detail

    private val args: MovieDetailFragmentArgs by navArgs()

    private val videoListAdapter by lazy { VideoListAdapter(this::goToWatchVideo) }
    private val castListAdapter by lazy { CastListAdapter(this::goToCast) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setMovieId(args.movieId)

        initAdapter()
        initObserve()
    }

    private fun initAdapter() {
        binding.videoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.videoList.adapter = videoListAdapter

        binding.castList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.castList.adapter = castListAdapter
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@MovieDetailFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
                launch {
                    viewModel.videoList.collect {
                        videoListAdapter.submitList(it?.results)
                    }
                }
                launch {
                    viewModel.castList.collect {
                        castListAdapter.submitList(it?.castList)
                    }
                }
            }
        }
    }

    private fun goToWatchVideo(video: Video) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(TheMovieConstants.getYoutubeWatchUrl(video.key)))
        requireContext().startActivity(intent)
    }

    private fun goToCast(cast: Cast) {
        val action = MovieDetailFragmentDirections.actionMovieDetailFragmentToCastDetailFragment(
            castId = cast.id,
            castName = cast.name
        )
        findNavController().navigate(action)
    }
}
