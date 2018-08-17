package com.fedesen.prueba.UI.searchOffline

import android.view.View
import com.fedesen.prueba.model.Genre
import com.fedesen.prueba.model.Movie

class SearchActivityPresenter(val view:SearchActivityContract.SearchActivityViewInterface) {

    fun getGenresTextList(genre: ArrayList<Genre>) {
        view.showLoading(View.INVISIBLE)

        var b : ArrayList<String> = ArrayList()
        for(g in genre){
            b.add(g.name)
        }
        view.onGenreListTextObtained(b)
    }

    fun getMoviesForGender(genre: Genre,movies:ArrayList<Movie>) {

        var matchedMovies:ArrayList<Movie> = ArrayList()
        for(m in movies){
            if(m.genreIds.contains(genre.id.toLong())){
                matchedMovies.add(m)
            }
        }
        view.onMoviesObtained(matchedMovies)

    }


}