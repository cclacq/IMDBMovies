package com.example.imdbmovies.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
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
class DetailViewModel @Inject constructor(
	private val repository: MovieRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	
	private val _movieState = MutableStateFlow<UiState<Movie>>(UiState.Loading)
	val movieState: StateFlow<UiState<Movie>> = _movieState
	
	init {
		val movieId = savedStateHandle.get<Int>("movieId") ?: 0
		fetchMovieDetails(movieId)
	}
	
	private fun fetchMovieDetails(id: Int) {
		viewModelScope.launch {
			_movieState.value = UiState.Loading
			try {
				val movieDetails = repository.getMovieDetails(id)
				_movieState.value = UiState.Success(movieDetails)
			} catch(e: Exception) {
				_movieState.value = UiState.Error("Failed to load movie details")
			}
		}
	}
}