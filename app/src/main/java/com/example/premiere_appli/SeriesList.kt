package com.example.premiere_appli

import SerieLight
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController

@Composable
fun SeriesListScreen(viewModel: MainViewModel, navController: NavController) {
    val series by viewModel.series.collectAsState()

    LaunchedEffect(series) {
        if (series.isEmpty()) {
            viewModel.getSeriesInitiaux()
        }
    }

    if (series.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(series) { serie ->
                SerieItem(serie = serie, onClick = {
                    navController.navigate("seriesDetail/${serie.id}")
                })
            }
        }
    } else {
        Text("Chargement des séries...", Modifier.padding(16.dp))
    }
}


@Composable
fun SerieItem(serie: SerieLight, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = serie.poster_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = serie.name ?: "Affiche de la série",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable { onClick() } // Navigation au clic
        )
        Text(
            text = serie.name ?: "Titre inconnu",
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = serie.first_air_date ?: "Date inconnue",
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

