package com.example.premiere_appli

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext

@Composable
fun FilmDetailScreen(viewModel: MainViewModel, filmId: String) {
    // Déclenche la récupération des détails du film
    LaunchedEffect(filmId) {
        viewModel.getFilmById(filmId)
    }

    // Observe les détails du film
    val film by viewModel.selectedFilm.collectAsState()

    if (film != null) {
        // Affichez les détails du film
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUrl = film!!.poster_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = film!!.title ?: "Affiche du film",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = film!!.title ?: "Titre inconnu",
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Date de sortie : ${film!!.release_date ?: "Date inconnue"}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = film!!.overview ?: "Description non disponible",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    } else {
        // Affichage d'un message de chargement ou d'erreur
        Text("Chargement des détails du film...", Modifier.padding(16.dp))
    }
}
