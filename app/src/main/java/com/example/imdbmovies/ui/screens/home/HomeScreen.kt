package com.example.imdbmovies.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.ui.components.atoms.SearchField
import com.example.imdbmovies.ui.components.organisms.MovieList
import com.example.imdbmovies.util.UiState
import com.example.imdbmovies.util.rememberDebouncedValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
	viewModel: HomeViewModel = hiltViewModel(),
	onMovieClick: (Movie) -> Unit
) {
	
	val state by viewModel.moviesState.collectAsState()
	val query by viewModel.query.collectAsState()
	
	var rawQuery by remember { mutableStateOf(query) }
	val debouncedQuery by rememberDebouncedValue(rawQuery)
	
	LaunchedEffect(debouncedQuery) {
		viewModel.searchMovies(debouncedQuery)
	}
	
	Scaffold (
		topBar = {
			TopAppBar(title = { Text("Movie Search App") })
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.fillMaxSize()
				.padding(8.dp)
		) {
			SearchField(
				value = rawQuery,
				onValueChange = { value ->
					rawQuery = value
				}
			)
			
			when(val currentState = state) {
				is UiState.Loading -> {
					Box(
						modifier = Modifier.fillMaxSize(),
						contentAlignment = Alignment.Center
					) {
						CircularProgressIndicator()
					}
				}
				
				is UiState.Success -> {
					if(currentState.data.isEmpty()) {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							Text("No results found.")
						}
					} else {
						MovieList(currentState.data, onItemClick = onMovieClick)
					}
				}
				
				is UiState.Error -> {
					Box(
						modifier = Modifier.fillMaxSize(),
						contentAlignment = Alignment.Center
					) {
						Text(currentState.message)
					}
				}
				
				else -> {}
			}
		}
	}
}