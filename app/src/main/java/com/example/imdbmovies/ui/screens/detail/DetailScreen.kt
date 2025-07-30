package com.example.imdbmovies.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.imdbmovies.util.Constants
import com.example.imdbmovies.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
	viewModel: DetailViewModel = hiltViewModel(),
	onBack: () -> Unit
) {
	val state by viewModel.movieState.collectAsState()
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Details") },
				navigationIcon = {
					IconButton (onClick = onBack) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
					}
				}
			)
		}
	) { padding ->
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
				val movie = currentState.data
				Column(
					modifier = Modifier
						.padding(padding)
						.verticalScroll(rememberScrollState())
				) {
					val posterUrl = movie.posterPath?.let { Constants.TMDB_IMAGE_BASE_URL + it }
					AsyncImage(
						model = posterUrl,
						contentDescription = "Poster van ${movie.title}",
						modifier = Modifier
							.fillMaxWidth()
							.height(300.dp)
					)
					Spacer(modifier = Modifier.height(16.dp))
					Text(
						"Released: ${movie.releaseDate}",
						style = MaterialTheme.typography.bodyMedium
					)
					Text(
						"Rating: ${movie.voteAverage}/10",
						style = MaterialTheme.typography.bodyMedium
					)
					Spacer(modifier = Modifier.height(10.dp))
					Text(movie.overview, style = MaterialTheme.typography.bodyLarge)
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