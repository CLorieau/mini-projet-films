package com.example.premiere_appli

import CastMember
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun FilmDetailScreen(viewModel: MainViewModel, filmId: String) {
    LaunchedEffect(filmId) {
        viewModel.getFilmById(filmId)
    }

    val film by viewModel.selectedFilm.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
    val credits = film?.credits

    if (film != null) {
        if (isLandscape) {
            // Ajout du défilement vertical pour le mode paysage
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Permet le défilement vertical en paysage
                    .padding(16.dp)
            ) {
                val imageUrl = film!!.poster_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = film!!.title ?: "Affiche du film",
                    modifier = Modifier
                        .weight(1f)
                        .height(300.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = film!!.title ?: "Titre inconnu",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Date de sortie : ${film!!.release_date ?: "Date inconnue"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = film!!.overview ?: "Description non disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (credits?.cast?.isNotEmpty() == true) {
                        Text(
                            text = "Acteurs :",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            items(credits.cast) { castMember ->
                                ActorCard(castMember)
                            }
                        }
                    } else {
                        Text(
                            text = "Aucun acteur disponible.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        } else {
            // Mode portrait avec défilement vertical
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Date de sortie : ${film!!.release_date ?: "Date inconnue"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = film!!.overview ?: "Description non disponible",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (credits?.cast?.isNotEmpty() == true) {
                    Text(
                        text = "Acteurs :",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(credits.cast) { castMember ->
                            ActorCard(castMember)
                        }
                    }
                } else {
                    Text(
                        text = "Aucun acteur disponible.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    } else {
        Text("Chargement des détails du film...", Modifier.padding(16.dp))
    }
}

@Composable
fun ActorCard(actor: CastMember) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = actor.profile_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = actor.name,
            modifier = Modifier
                .size(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = actor.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2
        )
        Text(
            text = actor.character,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1
        )
    }
}



