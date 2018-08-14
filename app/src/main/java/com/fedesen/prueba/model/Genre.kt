package com.fedesen.prueba.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class GenreResponse(
    @SerializedName("genres") val genres: List<Genre>
):Serializable

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
):Serializable