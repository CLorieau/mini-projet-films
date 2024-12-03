package com.example.premiere_appli

import FilmLight
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController


@Composable
fun FilmsListScreen(viewModel: MainViewModel, navController: NavController) {
    // Collecte de la liste des films
    val films by viewModel.movies.collectAsState()




    // Appel de la méthode pour récupérer les films si la liste est vide
    LaunchedEffect(films) {
        if (films.isEmpty()) {
            viewModel.getFilmsInitiaux()
        }
    }

    // Affichage de la grille des films
    if (films.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(films) { film ->
                FilmItem(film = film, onClick = {
                    navController.navigate("filmDetail/${film.id}")
                })
            }
        }
    } else {
        // Affichage d'un message de chargement si la liste est vide
        Text("Chargement des films...", Modifier.padding(16.dp))
    }
}

@Composable
fun FilmItem(film: FilmLight, onClick : () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = film.poster_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = film.title ?: "Affiche du film",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable { onClick() }
        )
        Text(
            text = film.title ?: "Titre inconnu",
            modifier = Modifier.padding(top = 8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center // Ajout de l'alignement centré
        )
        Text(
            text = film.release_date ?: "Date inconnue",
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center // Ajout de l'alignement centré si nécessaire
        )
    }
}


