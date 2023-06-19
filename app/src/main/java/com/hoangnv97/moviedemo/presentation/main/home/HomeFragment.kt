package com.hoangnv97.moviedemo.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.databinding.FragmentHomeBinding
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.domain.entity.MovieType
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import com.hoangnv97.moviedemo.presentation.common.adapter.LoadStateAdapter
import com.hoangnv97.moviedemo.presentation.common.adapter.MoviePagingAdapter
import com.hoangnv97.moviedemo.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_home

    private lateinit var popularListAdapter: PopularListAdapter
    private lateinit var movieAdapter: MoviePagingAdapter
    private lateinit var comingSoonAdapter: MoviePagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.show()

        initAdapter()
        initObserve()

        binding.hlMovieImage.setOnClickListener {
            goToDetail(viewModel.highlightedMovie.value)
        }
        binding.showAllMoviePopular.setOnClickListener {
            goToAll(MovieType.POPULAR)
        }
        binding.showAllMovies.setOnClickListener {
            goToAll(MovieType.MOVIES)
        }
        binding.showAllComingSoon.setOnClickListener {
            goToAll(MovieType.COMING_SOON)
        }
    }

    private fun initAdapter() {
        popularListAdapter = PopularListAdapter(this::getItemFirstPopular, this::goToDetail)
        binding.moviePopularList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moviePopularList.adapter = popularListAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(popularListAdapter),
            footer = LoadStateAdapter(popularListAdapter)
        )

        popularListAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    val error = ErrorObj(ApiEnum.API_POPULAR, (it.refresh as LoadState.Error).error)
                    error.retry = { popularListAdapter.retry() }
                    viewModel.setLoading(false)
                    viewModel.setFailed(error)
                }
                else -> {
                    if (popularListAdapter.itemCount == 0) {
                        viewModel.setLoading(it.refresh is LoadState.Loading)
                    } else {
                        viewModel.setLoading(false)
                    }
                }
            }
        }

        movieAdapter = MoviePagingAdapter(this::goToDetail)
        binding.movieList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.movieList.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(movieAdapter),
            footer = LoadStateAdapter(movieAdapter)
        )

        movieAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    val error =
                        ErrorObj(ApiEnum.API_MOVIE_LIST, (it.refresh as LoadState.Error).error)
                    error.retry = { movieAdapter.retry() }
                    viewModel.setLoading(false)
                    viewModel.setFailed(error)
                }
                else -> {
                    if (movieAdapter.itemCount == 0) {
                        viewModel.setLoading(it.refresh is LoadState.Loading)
                    } else {
                        viewModel.setLoading(false)
                    }
                }
            }
        }

        comingSoonAdapter = MoviePagingAdapter(this::goToDetail)
        binding.comingSoonList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.comingSoonList.adapter = comingSoonAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(comingSoonAdapter),
            footer = LoadStateAdapter(comingSoonAdapter)
        )

        comingSoonAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    val error =
                        ErrorObj(ApiEnum.API_COMING_SOON, (it.refresh as LoadState.Error).error)
                    error.retry = { comingSoonAdapter.retry() }
                    viewModel.setLoading(false)
                    viewModel.setFailed(error)
                }
                else -> {
                    if (comingSoonAdapter.itemCount == 0) {
                        viewModel.setLoading(it.refresh is LoadState.Loading)
                    } else {
                        viewModel.setLoading(false)
                    }
                }
            }
        }
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.popularList.collectLatest {
                        popularListAdapter.submitData(it)
                    }
                }
                launch {
                    viewModel.movieList.collectLatest {
                        movieAdapter.submitData(it)
                    }
                }
                launch {
                    viewModel.movieList.collectLatest {
                        comingSoonAdapter.submitData(it)
                    }
                }
                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@HomeFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
            }
        }
    }

    private fun getItemFirstPopular(movie: Movie) {
        viewModel.setHighlightMovie(movie)
    }

    private fun goToDetail(movie: Movie?) {
        movie?.let {
            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                movieId = it.id,
                movieTitle = it.title!!
            )
            findNavController().navigate(action)
        }
    }

    private fun goToAll(movieType: MovieType) {
        val action = HomeFragmentDirections.actionHomeFragmentToAllMovieFragment(movieType)
        findNavController().navigate(action)
    }
}
