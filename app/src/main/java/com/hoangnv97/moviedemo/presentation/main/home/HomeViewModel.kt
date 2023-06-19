package com.hoangnv97.moviedemo.presentation.main.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hoangnv97.moviedemo.Constants
import com.hoangnv97.moviedemo.domain.entity.GenreList
import com.hoangnv97.moviedemo.domain.entity.Movie
import com.hoangnv97.moviedemo.presentation.common.BaseViewModel
import com.hoangnv97.moviedemo.usecase.genre.GenreUseCase
import com.hoangnv97.moviedemo.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
    private val genreUseCase: GenreUseCase,
) : BaseViewModel() {

    init {
        getPopularList()
        getGenreList()
        getMovieList()
        getComingSoonList()
        viewModelScope.launch {
            genreUseCase.failed.collect {
                setFailed(it)
            }
        }
    }

    lateinit var popularList: StateFlow<PagingData<Movie>>
    lateinit var movieList: StateFlow<PagingData<Movie>>
    lateinit var comingSoonList: StateFlow<PagingData<Movie>>

    private val _highlightedMovie = MutableStateFlow<Movie?>(null)
    val highlightedMovie: StateFlow<Movie?> = _highlightedMovie

    fun setHighlightMovie(movie: Movie) {
        _highlightedMovie.value = movie
    }

    private val _genreList = MutableStateFlow<GenreList?>(null)
    val genreList: StateFlow<GenreList?> = _genreList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        null
    )

    private fun getPopularList() {
        viewModelScope.launch {
            popularList = useCase.getPopularList(10)
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope)
        }
    }

    private fun getMovieList() {
        viewModelScope.launch {
            movieList = useCase.getMovieList(10)
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope)
        }
    }

    private fun getComingSoonList() {
        viewModelScope.launch {
            comingSoonList = useCase.getComingSoonList(10)
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope)
        }
    }

    private fun getGenreList() {
        viewModelScope.launch {
            genreUseCase.execute(params = null) { getGenreList() }.collect {
                _genreList.value = it
            }
        }
    }

    val loading = combine(genreUseCase.loading, _loading) { list ->
        list.reduce { acc, b ->
            acc || b
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        false
    )

    val hlMovieGenreFirst = combine(highlightedMovie, genreList) { movie, genreList ->
        movie?.let {
            if (it.genreIds == null || it.genreIds.isEmpty()) return@let ""
            val genreId = it.genreIds[0]
            val data = genreList?.genreList?.find { genre -> genre.id.toInt() == genreId }
            data?.name ?: kotlin.run { "" }
        } ?: run { "" }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        ""
    )

    val hlMovieGenreSecond = combine(highlightedMovie, genreList) { movie, genreList ->
        movie?.let {
            if (it.genreIds == null || it.genreIds.size < 2) return@let ""
            val genreId = it.genreIds[1]
            val data = genreList?.genreList?.find { genre -> genre.id.toInt() == genreId }
            data?.name ?: kotlin.run { "" }
        } ?: run { "" }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(Constants.STOP_TIMEOUT_MILLIS),
        ""
    )
}
