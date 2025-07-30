package com.example.imdbmovies.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
fun rememberDebouncedValue(
	input: String,
	delayMillis: Long = 300L
): State<String> {
	val debounced = remember { mutableStateOf(input) }
	
	LaunchedEffect(input) {
		delay(delayMillis)
		debounced.value = input
	}
	
	return debounced
}