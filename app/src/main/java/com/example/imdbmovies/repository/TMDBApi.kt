package com.example.imdbmovies.repository

import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
	
	@GET("movie/popular")
	suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse
	
	@GET("search/movie")
	suspend fun searchMovies(
		@Query("query") query: String,
		@Query("api_key") apiKey: String
	): MovieResponse
	
	@GET("movie/{id}")
	suspend fun getMovieDetails(
		@Path("id") movieId: Int,
		@Query("api_key") apiKey: String
	): Movie
}