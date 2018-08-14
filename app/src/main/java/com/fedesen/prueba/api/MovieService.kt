package com.fedesen.prueba.api


import com.fedesen.prueba.model.GenreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieService {


    @GET("movie/upcoming/")
    fun getMovies(@Query("page")page:Int): Call<ListMoviesResponseWrapper>

    @GET("search/movie/")
    fun searchMovies(@Query("query") name: String): Call<SearchMoviesResponseWrapper>

    @GET("genre/movie/list?")
    fun getGenre(): Call<GenreResponse>


}


