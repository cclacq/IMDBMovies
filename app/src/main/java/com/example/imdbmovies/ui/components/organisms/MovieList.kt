package com.example.imdbmovies.ui.components.organisms

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.ui.components.molecules.MovieItem

@Composable
fun MovieList(movies: List<Movie>, onItemClick: (Movie) -> Unit) {
	LazyColumn {
		items(movies) { movie ->
			MovieItem(movie = movie) { onItemClick(movie) }
		}
	}
}