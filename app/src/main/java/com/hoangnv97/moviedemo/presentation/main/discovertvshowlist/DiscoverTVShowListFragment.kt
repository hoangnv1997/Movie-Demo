package com.hoangnv97.moviedemo.presentation.main.discovertvshowlist

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
import com.hoangnv97.moviedemo.databinding.FragmentDiscoverTVShowListBinding
import com.hoangnv97.moviedemo.domain.entity.TVShow
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.infra.wrapper.ErrorObj
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import com.hoangnv97.moviedemo.presentation.common.adapter.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverTVShowListFragment :
    BaseFragment<FragmentDiscoverTVShowListBinding, DiscoverTVShowListViewModel>() {

    override val viewModel: DiscoverTVShowListViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_discover_t_v_show_list

    private val discoverTVShowListAdapter by lazy {
        DiscoverTVShowListAdapter(this::goToTVShowDetail)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.discoverTVShowList.collectLatest {
                        discoverTVShowListAdapter.submitData(it)
                    }
                }
                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@DiscoverTVShowListFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        discoverTVShowListAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(discoverTVShowListAdapter),
            footer = LoadStateAdapter(discoverTVShowListAdapter)
        )
        binding.tvShowList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.tvShowList.adapter = discoverTVShowListAdapter
        discoverTVShowListAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    val error = ErrorObj(
                        ApiEnum.API_DISCOVER_TV_SHOW_LIST,
                        (it.refresh as LoadState.Error).error
                    )
                    error.retry = { discoverTVShowListAdapter.retry() }
                    viewModel.setLoading(false)
                    viewModel.setFailed(error)
                }
                else -> {
                    if (discoverTVShowListAdapter.itemCount == 0) {
                        viewModel.setLoading(it.refresh is LoadState.Loading)
                    } else {
                        viewModel.setLoading(false)
                    }
                }
            }
        }
    }

    private fun goToTVShowDetail(tvShow: TVShow) {
        val action =
            DiscoverTVShowListFragmentDirections
                .actionDiscoverTVShowListFragmentToTvShowDetailFragment(
                    tvShowId = tvShow.id,
                    tvShowName = tvShow.name!!
                )
        findNavController().navigate(action)
    }
}
