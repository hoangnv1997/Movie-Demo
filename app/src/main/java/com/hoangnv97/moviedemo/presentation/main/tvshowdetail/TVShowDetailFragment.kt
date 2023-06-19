package com.hoangnv97.moviedemo.presentation.main.tvshowdetail

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
import com.hoangnv97.moviedemo.databinding.FragmentTVShowDetailBinding
import com.hoangnv97.moviedemo.domain.api.TheMovieConstants
import com.hoangnv97.moviedemo.domain.entity.Cast
import com.hoangnv97.moviedemo.domain.entity.Video
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import com.hoangnv97.moviedemo.presentation.main.moviedetail.CastListAdapter
import com.hoangnv97.moviedemo.presentation.main.moviedetail.VideoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TVShowDetailFragment : BaseFragment<FragmentTVShowDetailBinding, TVShowDetailViewModel>() {

    override val viewModel: TVShowDetailViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_t_v_show_detail

    private val trailerListAdapter by lazy { VideoListAdapter(this::goToWatchVideo) }
    private val castListAdapter by lazy { CastListAdapter(this::goToCast) }

    private val args: TVShowDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setTVShowId(args.tvShowId)

        initAdapter()
        initObserve()
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@TVShowDetailFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
                launch {
                    viewModel.tvShowTrailerList.collect {
                        trailerListAdapter.submitList(it?.results)
                    }
                }
                launch {
                    viewModel.tvShowCastList.collect {
                        castListAdapter.submitList(it?.castList)
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        binding.trailerList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trailerList.adapter = trailerListAdapter

        binding.castList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.castList.adapter = castListAdapter
    }

    private fun goToWatchVideo(video: Video) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(TheMovieConstants.getYoutubeWatchUrl(video.key)))
        requireContext().startActivity(intent)
    }

    private fun goToCast(cast: Cast) {
        val action = TVShowDetailFragmentDirections.actionTvShowDetailFragmentToCastDetailFragment(
            castId = cast.id,
            castName = cast.name
        )
        findNavController().navigate(action)
    }
}
