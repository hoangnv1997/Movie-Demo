package com.hoangnv97.moviedemo.presentation.main.discovermovielist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.discovermovies.DiscoverMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class DiscoverMovieListViewModel @Inject constructor(
    private val useCase: DiscoverMovieListUseCase,
) : BaseViewModel() {

    init {
        getDiscoverMovieList()
    }

    lateinit var discoverMovieList: StateFlow<PagingData<Movie>>
    val loading: StateFlow<Boolean> = _loading.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )

    private fun getDiscoverMovieList() {
        viewModelScope.launch {
            discoverMovieList = useCase.getDiscoverMovieList(10)
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope)
        }
    }
}
