package com.hoangnv97.moviedemo.presentation.main.castdetail

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
import com.hoangnv97.moviedemo.databinding.FragmentCastDetailBinding
import com.hoangnv97.moviedemo.domain.api.TheMovieConstants
import com.hoangnv97.moviedemo.domain.entity.ImageCast
import com.hoangnv97.moviedemo.domain.entity.RelatedMovie
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CastDetailFragment : BaseFragment<FragmentCastDetailBinding, CastDetailViewModel>() {

    override val viewModel: CastDetailViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_cast_detail

    private val args: CastDetailFragmentArgs by navArgs()

    private val imageCastListAdapter by lazy { ImageCastListAdapter(this::goToWatchImage) }
    private val relatedMovieListAdapter by lazy { RelatedMovieListAdapter(this::goToMovieDetail) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setCastId(args.castId)

        initAdapter()
        initObserve()
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.imageCastList.collect {
                        imageCastListAdapter.submitList(it?.imageCastList)
                    }
                }
                launch {
                    viewModel.relatedMovieList.collect {
                        relatedMovieListAdapter.submitList(it?.relatedMovieList)
                    }
                }
                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@CastDetailFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        binding.imageCastList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.imageCastList.adapter = imageCastListAdapter

        binding.relatedMovieList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.relatedMovieList.adapter = relatedMovieListAdapter
    }

    private fun goToMovieDetail(relatedMovie: RelatedMovie) {
        if (relatedMovie.mediaType == "movie") {
            val action = CastDetailFragmentDirections.actionCastDetailFragmentToMovieDetailFragment(
                movieId = relatedMovie.id,
                movieTitle = relatedMovie.title
            )
            findNavController().navigate(action)
        } else if (relatedMovie.mediaType == "tv") {
            val action =
                CastDetailFragmentDirections.actionCastDetailFragmentToTvShowDetailFragment(
                    tvShowId = relatedMovie.id,
                    tvShowName = relatedMovie.title
                )
            findNavController().navigate(action)
        }
    }

    private fun goToWatchImage(imageCast: ImageCast) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(TheMovieConstants.getProfileUrl(imageCast.filePath))
        )
        requireContext().startActivity(intent)
    }
}
