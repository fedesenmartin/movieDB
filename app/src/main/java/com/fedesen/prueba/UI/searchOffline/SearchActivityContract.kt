package com.fedesen.prueba.UI.searchOffline

import android.annotation.SuppressLint
import com.fedesen.prueba.model.Genre
import com.fedesen.prueba.model.Movie

class SearchActivityContract{

    interface SearchActivityViewInterface{
        fun onGenreListTextObtained(b: MutableList<String>)
        fun onMoviesObtained(movie: ArrayList<Movie>){}
        fun showLoading(visible: Int)
        fun navigateDetails(movie:Movie){}

    }


}