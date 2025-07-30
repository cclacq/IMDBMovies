package com.example.imdbmovies.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.repository.MovieRepository
import com.example.imdbmovies.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val repository: MovieRepository
) : ViewModel() {
	
	private val _moviesState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
	val moviesState: StateFlow<UiState<List<Movie>>> = _moviesState
	
	private val _query = MutableStateFlow("")
	val query: StateFlow<String> = _query
	
	init {
		fetchPopularMovies()
	}
	
	private fun fetchPopularMovies() {
		viewModelScope.launch {
			_moviesState.value = UiState.Loading
			try {
				val popularMovies = repository.getPopularMovies()
				_moviesState.value = UiState.Success(popularMovies)
			} catch (e: Exception) {
				_moviesState.value = UiState.Error("Failed to load popular movies")
			}
		}
	}
	
	fun searchMovies(query: String) {
		_query.value = query
		
		if (query.isBlank()) {
			fetchPopularMovies()
			return
		}
		
		viewModelScope.launch {
			_moviesState.value = UiState.Loading
			try {
				val results = repository.searchMovies(query)
				_moviesState.value = UiState.Success(results)
			} catch (e: Exception) {
				_moviesState.value = UiState.Error("Search failed")
			}
		}
	}
}