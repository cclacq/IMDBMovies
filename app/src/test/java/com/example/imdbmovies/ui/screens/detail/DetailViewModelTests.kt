package com.example.imdbmovies.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
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
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
	
	private val testDispatcher = StandardTestDispatcher()
	
	private lateinit var repository: MovieRepository
	private lateinit var savedStateHandle: SavedStateHandle
	private lateinit var viewModel: DetailViewModel
	
	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		repository = mockk()
		savedStateHandle = SavedStateHandle(mapOf("movieId" to 1))
	}
	
	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}
	
	@Test
	fun `movieState should emit Success when repository returns movie`() = runTest {
		val movie = Movie(1, "Inception", "Description", "2010", "", 7.5)
		coEvery { repository.getMovieDetails(1) } returns movie
		
		viewModel = DetailViewModel(repository, savedStateHandle)
		
		runCurrent()
		
		val state = viewModel.movieState.value
		assertEquals(UiState.Success(movie), state)
	}
	
	@Test
	fun `movieState should emit Error when repository throws`() = runTest {
		coEvery { repository.getMovieDetails(1) } throws RuntimeException("Network error")
		
		viewModel = DetailViewModel(repository, savedStateHandle)
		
		runCurrent()
		
		val state = viewModel.movieState.value
		assertEquals(UiState.Error("Failed to load movie details"), state)
	}
	
	@Test
	fun `movieState should be Loading initially`() = runTest {
		coEvery { repository.getMovieDetails(any()) } coAnswers {
			Movie(0, "", "", "", "", 7.5)
		}
		
		viewModel = DetailViewModel(repository, savedStateHandle)
		
		val initialState = viewModel.movieState.value
		assertEquals(UiState.Loading, initialState)
	}
}