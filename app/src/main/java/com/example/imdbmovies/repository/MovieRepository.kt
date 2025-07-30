package com.example.imdbmovies.repository

import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.util.Constants
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: TMDBApi) {
	
	suspend fun getPopularMovies(): List<Movie> {
		val response = api.getPopularMovies(Constants.TMDB_API_KEY)
		return response.results
	}
	
	suspend fun searchMovies(query: String): List<Movie> {
		val response = api.searchMovies(query, Constants.TMDB_API_KEY)
		return response.results
	}
	
	suspend fun getMovieDetails(id: Int): Movie {
		return api.getMovieDetails(id, Constants.TMDB_API_KEY)
	}
}