package com.fedesen.prueba.data.api

import com.fedesen.prueba.model.Movie
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

// esta clase me sirve para hacer de wrapper para los resultados de las peliculas.
data class ListMoviesResponseWrapper(
        @SerializedName("page")
        @Expose
        var page: Long? = null,
        @SerializedName("results")
        @Expose
        var results: ArrayList<Movie> = ArrayList(),
        @SerializedName("total_results")
        @Expose
        var totalResults: Long? = null,
        @SerializedName("total_pages")
        @Expose
        var totalPages: Long? = null
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}
