data class TmdbFilms(
    val page: Int? = 0,
    val results: List<FilmLight> = listOf(),
    val total_pages: Int? = 0,
    val total_results: Int? = 0
)

data class FilmLight(
    val adult: Boolean? = false,
    val backdrop_path: String? = "",
    val genre_ids: List<Int?>? = listOf(),
    val id: Int? = 0,
    val media_type: String? = "",
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val poster_path: String? = "",
    val release_date: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0
)

data class TmdbSeries(
    val page: Int? = 0,
    val results: List<SerieLight> = listOf(),
    val total_pages: Int? = 0,
    val total_results: Int? = 0
)

data class SerieLight(
    val adult: Boolean? = false,
    val backdrop_path: String? = "",
    val first_air_date: String? = "",
    val genre_ids: List<Int?>? = listOf(),
    val id: Int? = 0,
    val media_type: String? = "",
    val name: String? = "",
    val origin_country: List<String?>? = listOf(),
    val original_language: String? = "",
    val original_name: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val poster_path: String? = "",
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0
)

data class TmdbActeurs(
    val page: Int? = 0,
    val results: List<ActeurLight> = listOf(),
    val total_pages: Int? = 0,
    val total_results: Int? = 0
)

data class ActeurLight(
    val adult: Boolean? = false,
    val gender: Int? = 0,
    val id: Int? = 0,
    val known_for_department: String? = "",
    val media_type: String? = "",
    val name: String? = "",
    val original_name: String? = "",
    val popularity: Double? = 0.0,
    val profile_path: String? = ""
)