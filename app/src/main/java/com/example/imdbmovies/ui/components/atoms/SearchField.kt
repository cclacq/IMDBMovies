package com.example.imdbmovies.ui.components.atoms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchField(value: String, onValueChange: (String) -> Unit) {
	TextField(
		value = value,
		onValueChange = onValueChange,
		placeholder = { Text("Search movies...") },
		modifier = Modifier.fillMaxWidth()
	)
}