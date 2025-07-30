package com.example.imdbmovies.ui.screens.home

import com.example.imdbmovies.model.Movie
import com.example.imdbmovies.repository.MovieRepository
import com.example.imdbmovies.util.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
	
	private val testDispatcher = StandardTestDispatcher()
	
	private lateinit var repository: MovieRepository
	private lateinit var viewModel: HomeViewModel
	
	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		repository = mockk()
	}
	
	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}
	
	@Test
	fun `initial load should emit Loading then Success with popular movies`() = runTest {
		val movies = listOf(Movie(1, "Movie 1", "Desc", "2020", "", 8.0))
		coEvery { repository.getPopularMovies() } returns movies
		
		viewModel = HomeViewModel(repository)
		
		runCurrent()
		
		assertEquals(UiState.Success(movies), viewModel.moviesState.value)
	}
	
	@Test
	fun `initial load should emit Error when repository throws`() = runTest {
		coEvery { repository.getPopularMovies() } throws RuntimeException("Network error")
		
		viewModel = HomeViewModel(repository)
		
		runCurrent()
		
		assertEquals(UiState.Error("Failed to load popular movies"), viewModel.moviesState.value)
	}
	
	@Test
	fun `searchMovies with blank query calls fetchPopularMovies`() = runTest {
		val popularMovies = listOf(Movie(1, "Popular", "Desc", "2021", "", 7.0))
		coEvery { repository.getPopularMovies() } returns popularMovies
		
		viewModel = HomeViewModel(repository)
		
		viewModel.searchMovies("")
		
		runCurrent()
		
		assertEquals(UiState.Success(popularMovies), viewModel.moviesState.value)
		assertEquals("", viewModel.query.value)
	}
	
	@Test
	fun `searchMovies with query emits Loading then Success`() = runTest {
		val query = "Inception"
		val results = listOf(Movie(2, "Inception", "Desc", "2010", "", 8.8))
		coEvery { repository.searchMovies(query) } returns results
		
		coEvery { repository.getPopularMovies() } returns emptyList()
		
		viewModel = HomeViewModel(repository)
		
		viewModel.searchMovies(query)
		
		runCurrent()
		
		assertEquals(UiState.Success(results), viewModel.moviesState.value)
		assertEquals(query, viewModel.query.value)
	}
	
	@Test
	fun `searchMovies with query emits Error when repository throws`() = runTest {
		val query = "Unknown"
		coEvery { repository.searchMovies(query) } throws RuntimeException("Search failed")
		coEvery { repository.getPopularMovies() } returns emptyList()
		
		viewModel = HomeViewModel(repository)
		
		viewModel.searchMovies(query)
		
		runCurrent()
		
		assertEquals(UiState.Error("Search failed"), viewModel.moviesState.value)
		assertEquals(query, viewModel.query.value)
	}
}