package com.example.imdbmovies.repository

import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.model.MovieResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {
	
	private lateinit var api: TMDBApi
	private lateinit var repository: MovieRepository
	
	@Before
	fun setup() {
		api = mockk()
		repository = MovieRepository(api)
	}
	
	@Test
	fun `getPopularMovies returns expected movie list`() = runBlocking {
		val expectedMovies = listOf(
			Movie(
				id = 1,
				title = "Movie 1",
				overview = "Overview",
				releaseDate = "10-01-1990",
				posterPath = "https://example.org",
				voteAverage = 7.5
			),
			Movie(id = 2,
				title = "Movie 2",
				overview = "Overview",
				releaseDate = "10-01-1990",
				posterPath = "https://example.org",
				voteAverage = 7.5
			)
		)
		val response = MovieResponse(results = expectedMovies)
		
		// Mock API call
		coEvery { api.getPopularMovies(any()) } returns response
		
		val movies = repository.getPopularMovies()
		
		assertEquals(expectedMovies, movies)
		coVerify (exactly = 1) { api.getPopularMovies(any()) }
	}
	
	@Test
	fun `searchMovies returns expected movie list`() = runBlocking {
		val query = "test"
		val expectedMovies = listOf(
			Movie(id = 3,
				title = "Search Result 1",
				overview = "Overview",
				releaseDate = "10-01-1990",
				posterPath = "https://example.org",
				voteAverage = 7.5
			),
			Movie(id = 4,
				title = "Search Result 2",
				overview = "Overview",
				releaseDate = "10-01-1990",
				posterPath = "https://example.org",
				voteAverage = 7.5)
		)
		val response = MovieResponse(results = expectedMovies)
		
		coEvery { api.searchMovies(query, any()) } returns response
		
		val movies = repository.searchMovies(query)
		
		assertEquals(expectedMovies, movies)
		coVerify(exactly = 1) { api.searchMovies(query, any()) }
	}
	
	@Test
	fun `getMovieDetails returns expected movie`() = runBlocking {
		val movieId = 5
		val expectedMovie = Movie(id = movieId,
			title = "Details Movie",
			overview = "Overview",
			releaseDate = "10-01-1990",
			posterPath = "https://example.org",
			voteAverage = 7.5)
		
		coEvery { api.getMovieDetails(movieId, any()) } returns expectedMovie
		
		val movie = repository.getMovieDetails(movieId)
		
		assertEquals(expectedMovie, movie)
		coVerify(exactly = 1) { api.getMovieDetails(movieId, any()) }
	}
}