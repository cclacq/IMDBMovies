package com.example.imdbmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imdbmovies.ui.screens.detail.DetailScreen
import com.example.imdbmovies.ui.screens.home.HomeScreen
import com.example.imdbmovies.ui.theme.IMDBMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			IMDBMoviesTheme {
				MainScreen()
			}
		}
	}
}

@Composable
fun MainScreen() {
	val navController = rememberNavController()
	
	Scaffold(
		modifier = Modifier.fillMaxSize()
	) { innerPadding ->
		NavHost(
			navController = navController,
			startDestination = "home",
			modifier = Modifier.padding(innerPadding)
		) {
			composable("home") {
				HomeScreen(
					onMovieClick = { movie ->
						navController.navigate("detail/${movie.id}")
					}
				)
			}
			composable(
				"detail/{movieId}",
				arguments = listOf(navArgument("movieId") { type = NavType.IntType })
			) {
				DetailScreen(onBack = { navController.popBackStack() })
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	IMDBMoviesTheme {
		MainScreen()
	}
}