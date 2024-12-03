package com.example.premiere_appli

import ActeurLight
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext

@Composable
fun ActeursListScreen(viewModel: MainViewModel) {
    // Collecte de la liste des acteurs/personnalités
    val acteurs by viewModel.acteurs.collectAsState()



    // Appel de la méthode pour récupérer les acteurs si la liste est vide
    LaunchedEffect(acteurs) {
        if (acteurs.isEmpty()) {
            viewModel.getActeursInitiaux()
        }
    }

    // Affichage de la grille des acteurs/personnalités
    if (acteurs.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(acteurs) { acteur ->
                ActeurItem(acteur)
            }
        }
    } else {
        // Affichage d'un message de chargement si la liste est vide
        Text("Chargement des acteurs...", Modifier.padding(16.dp))
    }
}

@Composable
fun ActeurItem(acteur: ActeurLight) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Utilisation de Coil pour charger l'image de l'affiche
        val imageUrl = acteur.profile_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = acteur.name ?: "Photo de l'acteur",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        // Affichage du nom de l'acteur/personnalité
        Text(
            text = acteur.name ?: "Nom inconnu",
            modifier = Modifier.padding(top = 8.dp)
        )
        // Affichage du texte supplémentaire, si nécessaire
        Text(
            text = acteur.known_for_department ?: "Domaine inconnu",
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
