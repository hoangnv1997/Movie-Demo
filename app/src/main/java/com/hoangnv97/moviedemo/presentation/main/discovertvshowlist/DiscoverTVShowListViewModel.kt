package com.hoangnv97.moviedemo.presentation.main.discovertvshowlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.entity.TVShow
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.discovertvshows.DiscoverTVShowListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class DiscoverTVShowListViewModel @Inject constructor(
    private val useCase: DiscoverTVShowListUseCase,
) : BaseViewModel() {

    init {
        getDiscoverTVShowList()
    }

    lateinit var discoverTVShowList: StateFlow<PagingData<TVShow>>
    val loading: StateFlow<Boolean> = _loading.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )

    private fun getDiscoverTVShowList() {
        viewModelScope.launch {
            discoverTVShowList = useCase.getDiscoverTVShowList(10)
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope)
        }
    }
}
