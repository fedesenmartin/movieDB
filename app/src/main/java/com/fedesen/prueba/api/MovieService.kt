package com.fedesen.prueba.api


import com.fedesen.prueba.model.GenreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MovieService {

    @GET("movie/top_rated")
    fun getTopRated(): Call<ListMoviesResponseWrapper>

    @GET("movie/upcoming/")
    fun getUpcoming(): Call<ListMoviesResponseWrapper>

    @GET("movie/popular")
    fun getPopular(): Call<ListMoviesResponseWrapper>

    @GET("search/movie/")
    fun searchMovies(@Query("query") name: String): Call<SearchMoviesResponseWrapper>

    @GET("genre/movie/list?")
    fun getGenre(): Call<GenreResponse>


}


