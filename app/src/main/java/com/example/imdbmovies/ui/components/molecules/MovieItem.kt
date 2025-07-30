package com.example.imdbmovies.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.util.Constants

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
	Card (
		modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp)
			.clickable { onClick() }
	) {
		Row {
			val posterUrl = movie.posterPath?.let { Constants.TMDB_IMAGE_BASE_URL + it }
			AsyncImage(
				model = posterUrl,
				contentDescription = movie.title
			)
			Column(modifier = Modifier.padding(8.dp)) {
				Text(movie.title, style = MaterialTheme.typography.titleMedium)
				Text("Release: ${movie.releaseDate}")
			}
		}
	}
}