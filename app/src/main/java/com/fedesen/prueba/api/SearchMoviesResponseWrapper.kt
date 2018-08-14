package com.fedesen.prueba.api

import com.fedesen.prueba.model.Movie
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchMoviesResponseWrapper(
        @SerializedName("page")
        val page: Int = 0,
        @SerializedName("results")
        val results: ArrayList<Movie> = ArrayList<Movie>(),
        @SerializedName("total_results")
        val totalResults: Int = 0,
        @SerializedName("total_pages")
        val totalPages: Int = 0
) : Serializable {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}
