package com.hoangnv97.moviedemo.presentation.main.moviedetail

import androidx.lifecycle.viewModelScope
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.entity.CastList
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.domain.entity.VideoList
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.castlist.CastListUseCase
import com.hoangnv97.moviedemo.usecase.moviedetail.MovieDetailUseCase
import com.hoangnv97.moviedemo.usecase.videolist.VideoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase,
    private val videoListUseCase: VideoListUseCase,
    private val castListUseCase: CastListUseCase,
) : BaseViewModel() {

    private val _movieId = MutableStateFlow(0)
    fun setMovieId(movieId: Int) {
        _movieId.value = movieId
    }

    init {
        viewModelScope.launch {
            _movieId.collect {
                if (it != 0) {
                    getMovieDetail(it)
                    getVideoList(it)
                    getCastList(it)
                }
            }
        }
        viewModelScope.launch {
            movieDetailUseCase.failed.collect {
                setFailed(it)
            }
        }
        viewModelScope.launch {
            videoListUseCase.failed.collect {
                setFailed(it)
            }
        }
        viewModelScope.launch {
            castListUseCase.failed.collect {
                setFailed(it)
            }
        }
    }

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieDetailUseCase.execute(movieId).collect {
                _movie.value = it
            }
        }
    }

    private val _videoList = MutableStateFlow<VideoList?>(null)
    val videoList: StateFlow<VideoList?> = _videoList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getVideoList(movieId: Int) {
        viewModelScope.launch {
            videoListUseCase.execute(movieId).collect {
                _videoList.value = it
            }
        }
    }

    private val _castList = MutableStateFlow<CastList?>(null)
    val castList: StateFlow<CastList?> = _castList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getCastList(movieId: Int) {
        viewModelScope.launch {
            castListUseCase.execute(movieId).collect {
                _castList.value = it
            }
        }
    }

    val loading = combine(
        movieDetailUseCase.loading,
        videoListUseCase.loading,
        castListUseCase.loading
    ) { list ->
        list.reduce { acc, b ->
            acc || b
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )
}
