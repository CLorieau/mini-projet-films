import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("trending/movie/week")
    suspend fun lastmovies(
        @Query("api_key") api_key: String
    ): TmdbFilms

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): TmdbFilms

    @GET("trending/tv/week")
    suspend fun getTrendingSeries(
        @Query("api_key") apiKey: String
    ): TmdbSeries

    @GET("trending/person/week")
    suspend fun getTrendingActeurs(
        @Query("api_key") apiKey: String
    ): TmdbActeurs

    @GET("search/tv")
    suspend fun searchSeries(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): TmdbSeries

    @GET("search/person")
    suspend fun searchActeurs(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): TmdbActeurs

    @GET("movie/{movie_id}")
    suspend fun getFilmDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): FilmLight
}





