package com.hoangnv97.moviedemo.presentation.main.allmovie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.databinding.FragmentAllMovieBinding
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
class AllMovieFragment : BaseFragment<FragmentAllMovieBinding, AllMovieViewModel>() {

    override val viewModel: AllMovieViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_all_movie

    private val args: AllMovieFragmentArgs by navArgs()

    private val movieListAdapter by lazy { MoviePagingAdapter(this::goToMovieDetail) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setMovieType(args.movieType)

        initAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movieList.collectLatest { pagingData ->
                        pagingData?.let {
                            movieListAdapter.submitData(it)
                        }
                    }
                }
                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@AllMovieFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        movieListAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(movieListAdapter),
            footer = LoadStateAdapter(movieListAdapter)
        )
        binding.allMovie.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.allMovie.adapter = movieListAdapter

        movieListAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    val error =
                        ErrorObj(ApiEnum.UNCLASSIFIED, (it.refresh as LoadState.Error).error)
                    error.retry = { movieListAdapter.retry() }
                    viewModel.setLoading(false)
                    viewModel.setFailed(error)
                }
                else -> {
                    if (movieListAdapter.itemCount == 0) {
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
            val action = AllMovieFragmentDirections.actionAllMovieFragmentToMovieDetailFragment(
                movieId = it.id,
                movieTitle = it.title!!
            )
            findNavController().navigate(action)
        }
    }
}
