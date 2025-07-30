package com.example.imdbmovies.repository

import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.model.MovieResponse
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TMDBApiTest {
	
	private lateinit var mockWebServer: MockWebServer
	private lateinit var api: TMDBApi
	private val gson = Gson()
	
	@Before
	fun setup() {
		mockWebServer = MockWebServer()
		mockWebServer.start()
		
		api = Retrofit.Builder()
			.baseUrl(mockWebServer.url("/"))
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(TMDBApi::class.java)
	}
	
	@After
	fun teardown() {
		mockWebServer.shutdown()
	}
	
	@Test
	fun `getPopularMovies returns parsed movie list`() = runBlocking {
		val movies = listOf(
			Movie(id = 1,
				title = "Movie 1",
				overview = "Overview",
				releaseDate = "10-01-1990",
				posterPath = "https://example.org",
				voteAverage = 7.5),
			Movie(id = 2,
				title = "Movie 2",
				overview = "Overview",
				releaseDate = "10-01-1990",
				posterPath = "https://example.org",
				voteAverage = 7.5)
		)
		val mockResponse = MovieResponse(results = movies)
		
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(gson.toJson(mockResponse))
		)
		
		val result = api.getPopularMovies("dummy_key")
		
		assertEquals(2, result.results.size)
		assertEquals("Movie 1", result.results[0].title)
		assertEquals(1, result.results[0].id)
	}
	
	@Test
	fun `searchMovies returns expected results`() = runBlocking {
		val movies = listOf(
			Movie(id = 3,
				title = "Search Match 1",
				overview = "Overview",
				releaseDate = "10-01-1990",
				posterPath = "https://example.org",
				voteAverage = 7.5),
			Movie(id = 4,
				title = "Search Match 2",
				overview = "Overview",
				releaseDate = "10-01-1990",
				posterPath = "https://example.org",
				voteAverage = 7.5)
		)
		val mockResponse = MovieResponse(results = movies)
		
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(gson.toJson(mockResponse))
		)
		
		val result = api.searchMovies(query = "test", apiKey = "dummy_key")
		
		assertEquals(2, result.results.size)
		assertEquals("Search Match 1", result.results[0].title)
	}
	
	@Test
	fun `getMovieDetails returns single movie`() = runBlocking {
		val movie = Movie(id = 10,
			title = "Movie Details Title",
			overview = "Overview",
			releaseDate = "10-01-1990",
			posterPath = "https://example.org",
			voteAverage = 7.5)
		
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(200)
				.setBody(gson.toJson(movie))
		)
		
		val result = api.getMovieDetails(movieId = 10, apiKey = "dummy_key")
		
		assertEquals(10, result.id)
		assertEquals("Movie Details Title", result.title)
	}
}