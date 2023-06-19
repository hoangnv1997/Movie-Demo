package com.hoangnv97.moviedemo.presentation.main.discovermovielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.databinding.FragmentDiscoverMovieListBinding
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import com.hoangnv97.moviedemo.presentation.common.adapter.LoadStateAdapter
import com.hoangnv97.moviedemo.presentation.common.adapter.MoviePagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverMovieListFragment :
    BaseFragment<FragmentDiscoverMovieListBinding, DiscoverMovieListViewModel>() {

    override val viewModel: DiscoverMovieListViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_discover_movie_list

    private val discoverMovieListAdapter by lazy { MoviePagingAdapter(this::goToMovieDetail) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.discoverMovieList.collectLatest {
                        discoverMovieListAdapter.submitData(it)
                    }
                }

                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@DiscoverMovieListFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        discoverMovieListAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(discoverMovieListAdapter),
            footer = LoadStateAdapter(discoverMovieListAdapter)
        )
        binding.discoverMovies.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.discoverMovies.adapter = discoverMovieListAdapter

        discoverMovieListAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    val error = ErrorObj(
                        ApiEnum.API_DISCOVER_MOVIES,
                        (it.refresh as LoadState.Error).error
                    )
                    error.retry = { discoverMovieListAdapter.retry() }
                    viewModel.setLoading(false)
                    viewModel.setFailed(error)
                }
                else -> {
                    if (discoverMovieListAdapter.itemCount == 0) {
                        viewModel.setLoading(it.refresh is LoadState.Loading)
                    } else {
                        viewModel.setLoading(false)
                    }
                }
            }
        }
    }

    private fun goToMovieDetail(movie: Movie?) {
        movie?.let {
            val action =
                DiscoverMovieListFragmentDirections
                    .actionDiscoverMovieListFragmentToMovieDetailFragment(
                        movieId = it.id,
                        movieTitle = it.title!!
                    )
            findNavController().navigate(action)
        }
    }
}
