package com.hoangnv97.moviedemo.presentation.main.allmovie

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.domain.entity.MovieType
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AllMovieViewModel @Inject constructor(
    private val useCase: HomeUseCase,
) : BaseViewModel() {

    private val _movieType = MutableStateFlow(MovieType.UNKNOWN)
    val movieType: StateFlow<MovieType> = _movieType.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        MovieType.UNKNOWN
    )

    fun setMovieType(movieType: MovieType) {
        _movieType.value = movieType
    }

    private val _movieList = MutableStateFlow<PagingData<Movie>?>(null)
    val movieList: StateFlow<PagingData<Movie>?> = _movieList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    init {
        viewModelScope.launch {
            movieType.collect {
                when (it) {
                    MovieType.POPULAR -> {
                        getPopularList()
                    }
                    MovieType.MOVIES -> {
                        getMovieList()
                    }
                    MovieType.COMING_SOON -> {
                        getComingSoonList()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getPopularList() {
        viewModelScope.launch {
            useCase.getPopularList(10)
                .cachedIn(viewModelScope).collect {
                    _movieList.value = it
                }
        }
    }

    private fun getMovieList() {
        viewModelScope.launch {
            useCase.getMovieList(10)
                .cachedIn(viewModelScope).collect {
                    _movieList.value = it
                }
        }
    }

    private fun getComingSoonList() {
        viewModelScope.launch {
            useCase.getComingSoonList(10)
                .cachedIn(viewModelScope).collect {
                    _movieList.value = it
                }
        }
    }

    val loading: StateFlow<Boolean> = _loading.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )
}
