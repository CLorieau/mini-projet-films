package com.example.premiere_appli

import ActeurLight
import Api
import FilmLight
import Playlist
import SerieLight
import TmdbFilms
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtest.playlistjson
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    val movies = MutableStateFlow<List<FilmLight>>(listOf())
    val series = MutableStateFlow<List<SerieLight>>(listOf())
    val acteurs = MutableStateFlow<List<ActeurLight>>(listOf())
    val playlist = MutableStateFlow<List<Playlist>>(listOf())
    val api_key = "b229e69561a342c12d5048557ba6e35f"
    private val _selectedFilm = MutableStateFlow<FilmLight?>(null)
    val selectedFilm: StateFlow<FilmLight?> = _selectedFilm
    private val _movies = MutableStateFlow<List<TmdbFilms>>(listOf())
    private val _selectedSerie = MutableStateFlow<SerieLight?>(null)
    val selectedSerie: StateFlow<SerieLight?> = _selectedSerie


    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    val api = retrofit.create(Api::class.java)

    fun getFilmsInitiaux() {
        viewModelScope.launch {
            movies.value = api.lastmovies(api_key).results

        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            movies.value = api.searchMovies(api_key, query).results

        }
    }

    fun getSeriesInitiaux() {
        viewModelScope.launch {
            series.value = api.getTrendingSeries(api_key).results

        }
    }

    fun getActeursInitiaux() {
        viewModelScope.launch {
            acteurs.value = api.getTrendingActeurs(api_key).results

        }
    }

    fun searchSeries(query: String) {
        viewModelScope.launch {
            series.value = api.searchSeries(api_key, query).results

        }
    }

    fun searchActeurs(query: String) {
        viewModelScope.launch {
            acteurs.value = api.searchActeurs(api_key, query).results

        }
    }

    fun getFilmById(filmId: String) {
        viewModelScope.launch {
            try {
                _selectedFilm.value = api.getFilmDetails(
                    filmId,
                    api_key,
                    appendToResponse = "credits")
            } catch (e: Exception) {
                _selectedFilm.value = null
            }
        }
    }

    fun getSerieById(seriesId: String) {
        viewModelScope.launch {
            try {
                _selectedSerie.value = api.getSeriesDetails(
                    seriesId,
                    api_key,
                    appendToResponse = "credits")
            } catch (e: Exception) {
                _selectedSerie.value = null
            }
        }
    }

    fun fetchPlaylist() : Playlist {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(Playlist::class.java).fromJson(playlistjson)!!
    }

    fun getPlaylist () {
        viewModelScope.launch {
            playlist.value = getPlaylist()

        }
    }
}