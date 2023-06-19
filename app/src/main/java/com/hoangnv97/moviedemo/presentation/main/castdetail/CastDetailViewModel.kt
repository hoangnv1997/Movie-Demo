package com.hoangnv97.moviedemo.presentation.main.castdetail

import androidx.lifecycle.viewModelScope
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.entity.CastInfo
import com.hoangnv97.moviedemo.domain.entity.ImageCastList
import com.hoangnv97.moviedemo.domain.entity.RelatedMovieList
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.castinfo.CastInfoUseCase
import com.hoangnv97.moviedemo.usecase.imagecastlist.ImageCastListUseCase
import com.hoangnv97.moviedemo.usecase.relatedmovielist.RelatedMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class CastDetailViewModel @Inject constructor(
    private val castInfoUseCase: CastInfoUseCase,
    private val imageCastListUseCase: ImageCastListUseCase,
    private val relatedMovieListUseCase: RelatedMovieListUseCase,
) : BaseViewModel() {

    private val _castId = MutableStateFlow(0)
    fun setCastId(castId: Int) {
        _castId.value = castId
    }

    init {
        viewModelScope.launch {
            _castId.collect {
                if (it != 0) {
                    getCastInfo(it)
                    getImageCastList(it)
                    getRelatedMovieList(it)
                }
            }
        }
        viewModelScope.launch {
            castInfoUseCase.failed.collect {
                setFailed(it)
            }
        }
        viewModelScope.launch {
            imageCastListUseCase.failed.collect {
                setFailed(it)
            }
        }
        viewModelScope.launch {
            relatedMovieListUseCase.failed.collect {
                setFailed(it)
            }
        }
    }

    private val _castInfo = MutableStateFlow<CastInfo?>(null)
    val castInfo: StateFlow<CastInfo?> = _castInfo.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getCastInfo(castId: Int) {
        viewModelScope.launch {
            castInfoUseCase.execute(castId).collect {
                _castInfo.value = it
            }
        }
    }

    private val _imageCastList = MutableStateFlow<ImageCastList?>(null)
    val imageCastList: StateFlow<ImageCastList?> = _imageCastList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getImageCastList(castId: Int) {
        viewModelScope.launch {
            imageCastListUseCase.execute(castId).collect {
                _imageCastList.value = it
            }
        }
    }

    private val _relatedMovieList = MutableStateFlow<RelatedMovieList?>(null)
    val relatedMovieList: StateFlow<RelatedMovieList?> = _relatedMovieList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getRelatedMovieList(castId: Int) {
        viewModelScope.launch {
            relatedMovieListUseCase.execute(castId).collect {
                _relatedMovieList.value = it
            }
        }
    }

    val loading = combine(
        castInfoUseCase.loading,
        imageCastListUseCase.loading,
        relatedMovieListUseCase.loading
    ) { list -> list.reduce { acc, b -> acc || b } }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )
}
