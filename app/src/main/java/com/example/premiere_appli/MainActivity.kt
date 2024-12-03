package com.example.premiere_appli


import SerieDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.premiere_appli.ui.theme.Premiere_appliTheme
import kotlinx.serialization.Serializable
import androidx.navigation.NavDestination.Companion.hasRoute

@kotlinx.serialization.Serializable
class ProfilDest

@Serializable
class FilmsListDest

@Serializable
class SeriesListDest

@Serializable
class ActeursListDest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(viewModel: MainViewModel, currentDestination: String?) {
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    TopAppBar(
        title = {
            if (isSearching) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    placeholder = { Text("Rechercher...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text("Recherche")
            }
        },
        actions = {
            IconButton(onClick = {
                if (isSearching) {
                    // Effectue la recherche selon la page actuelle
                    when (currentDestination) {
                        FilmsListDest::class.java.simpleName -> {
                            viewModel.searchMovies(searchQuery.text)
                        }

                        SeriesListDest::class.java.simpleName -> {
                            viewModel.searchSeries(searchQuery.text)
                        }

                        ActeursListDest::class.java.simpleName -> {
                            viewModel.searchActeurs(searchQuery.text)
                        }

                        else -> {
                            // Pas de recherche ou gestion par défaut
                        }
                    }
                    // Réinitialisation du champ de recherche
                    isSearching = false
                    searchQuery = TextFieldValue("")
                } else {
                    isSearching = true
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.search), // Remplacez avec une icône valide
                    contentDescription = "Recherche"
                )
            }
        }
    )
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

            Premiere_appliTheme {
                // Utilisation du ViewModel lié au cycle de vie
                val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        when (currentDestination?.route) {
                            "filmDetail/{filmId}" -> {
                                TopAppBar(
                                    title = { Text("Détail du film") },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.back),
                                                contentDescription = "Retour"
                                            )
                                        }
                                    }
                                )
                            }
                            "seriesDetail/{seriesId}" -> {
                                TopAppBar(
                                    title = { Text("Détail de la série") },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.back),
                                                contentDescription = "Retour"
                                            )
                                        }
                                    }
                                )
                            }

                            ProfilDest::class.java.simpleName -> {

                            }

                            else -> {
                                // Affichage de la barre de recherche pour toutes les autres pages
                                SearchTopBar(
                                    viewModel = viewModel,
                                    currentDestination = currentDestination?.route
                                )
                            }
                        }
                    },
                    bottomBar = {
                        // Affiche la NavigationBar uniquement si l'écran actuel n'est pas la page Profil
                        if (currentDestination?.route != ProfilDest::class.java.simpleName) {
                            NavigationBar {
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.films),
                                            contentDescription = "movies"
                                        )
                                    },
                                    label = { Text("Films") },
                                    selected = currentDestination?.route == FilmsListDest::class.java.simpleName,
                                    onClick = { navController.navigate(FilmsListDest::class.java.simpleName) }
                                )
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.series),
                                            contentDescription = "series"
                                        )
                                    },
                                    label = { Text("Series") },
                                    selected = currentDestination?.route == SeriesListDest::class.java.simpleName,
                                    onClick = { navController.navigate(SeriesListDest::class.java.simpleName) }
                                )
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_person_24),
                                            contentDescription = "actors"
                                        )
                                    },
                                    label = { Text("Acteurs") },
                                    selected = currentDestination?.route == ActeursListDest::class.java.simpleName,
                                    onClick = { navController.navigate(ActeursListDest::class.java.simpleName) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = ProfilDest::class.java.simpleName,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(ProfilDest::class.java.simpleName) {
                            ProfilScreen(
                                classes = windowSizeClass,
                                innerPadding = innerPadding,
                                navController
                            )
                        }
                        composable(FilmsListDest::class.java.simpleName) {
                            FilmsListScreen(viewModel = viewModel, navController = navController)
                        }
                        composable(SeriesListDest::class.java.simpleName) {
                            SeriesListScreen(viewModel = viewModel, navController = navController)
                        }
                        composable(ActeursListDest::class.java.simpleName) {
                            ActeursListScreen(viewModel = viewModel)
                        }
                        composable("filmDetail/{filmId}") { backStackEntry ->
                            val filmId = backStackEntry.arguments?.getString("filmId") ?: ""
                            FilmDetailScreen(viewModel = viewModel, filmId = filmId)
                        }
                        composable("seriesDetail/{seriesId}") { backStackEntry ->
                            val seriesId = backStackEntry.arguments?.getString("seriesId") ?: ""
                            SerieDetailScreen(viewModel = viewModel, seriesId = seriesId)

                        }
                    }
                }
            }
        }
    }
}



