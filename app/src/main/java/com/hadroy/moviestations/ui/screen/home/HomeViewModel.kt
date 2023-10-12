package com.hadroy.moviestations.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadroy.moviestations.data.MovieRepository
import com.hadroy.moviestations.data.model.Movie
import com.hadroy.moviestations.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Movie>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movie>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getALlMovies() {
        viewModelScope.launch {
            repository.getMovies()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { listMovies ->
                    _uiState.value = UiState.Success(listMovies)
                }
        }
    }

    fun search(newQuery: String) {
        UiState.Loading
        _query.value = newQuery
        _uiState.value = UiState.Success(repository.searchMovieByTitle(_query.value))
    }
}