package com.hoangnv97.moviedemo.presentation.main.tvshowdetail

import androidx.lifecycle.viewModelScope
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.entity.CastList
import com.hoangnv97.moviedemo.domain.entity.TVShow
import com.hoangnv97.moviedemo.domain.entity.VideoList
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.tvshowcastlist.TVShowCastListUseCase
import com.hoangnv97.moviedemo.usecase.tvshowdetail.TVShowDetailUseCase
import com.hoangnv97.moviedemo.usecase.tvshowvideolist.TVShowVideoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TVShowDetailViewModel @Inject constructor(
    private val tvShowDetailUseCase: TVShowDetailUseCase,
    private val tvShowVideoListUseCase: TVShowVideoListUseCase,
    private val tvShowCastListUseCase: TVShowCastListUseCase,
) : BaseViewModel() {

    private val _tvShowId = MutableStateFlow(0)
    fun setTVShowId(tvShowId: Int) {
        _tvShowId.value = tvShowId
    }

    init {
        viewModelScope.launch {
            _tvShowId.collect {
                if (it != 0) {
                    getTVShowDetail(it)
                    getTVShowVideoList(it)
                    getTVShowCastList(it)
                }
            }
        }
        viewModelScope.launch {
            tvShowDetailUseCase.failed.collect {
                setFailed(it)
            }
        }
        viewModelScope.launch {
            tvShowVideoListUseCase.failed.collect {
                setFailed(it)
            }
        }
        viewModelScope.launch {
            tvShowCastListUseCase.failed.collect {
                setFailed(it)
            }
        }
    }

    private val _tvShow = MutableStateFlow<TVShow?>(null)
    val tvShow: StateFlow<TVShow?> = _tvShow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getTVShowDetail(tvShowId: Int) {
        viewModelScope.launch {
            tvShowDetailUseCase.execute(tvShowId).collect {
                _tvShow.value = it
            }
        }
    }

    private val _tvShowTrailerList = MutableStateFlow<VideoList?>(null)
    val tvShowTrailerList: StateFlow<VideoList?> = _tvShowTrailerList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getTVShowVideoList(tvShowId: Int) {
        viewModelScope.launch {
            tvShowVideoListUseCase.execute(tvShowId).collect {
                _tvShowTrailerList.value = it
            }
        }
    }

    private val _tvShowCastList = MutableStateFlow<CastList?>(null)
    val tvShowCastList: StateFlow<CastList?> = _tvShowCastList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getTVShowCastList(movieId: Int) {
        viewModelScope.launch {
            tvShowCastListUseCase.execute(movieId).collect {
                _tvShowCastList.value = it
            }
        }
    }

    val loading = combine(
        tvShowDetailUseCase.loading,
        tvShowVideoListUseCase.loading,
        tvShowVideoListUseCase.loading
    ) { list ->
        list.reduce { acc, b -> acc || b }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )
}
