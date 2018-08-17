package com.fedesen.prueba.UI.home

import android.support.v7.app.AppCompatActivity
import com.fedesen.prueba.adapter.AdapterMovie
import com.fedesen.prueba.model.Genre
import com.fedesen.prueba.model.Movie


class MainActivityContract {


     interface MainActivityViewInterface{
         fun onMoviesObtained(movie: ArrayList<Movie>,page_number : Int){}
         fun setLoaderVisiblity(visibility:Int) {}
         fun onError(s: String)
         fun navigateDetails(movie:Movie){}
         fun navigateSearch(genreList:ArrayList<Genre>?,movie: ArrayList<Movie>?){}
         fun onSearhObtainedMovies(movies: ArrayList<Movie>){}
         fun showSearchLayout(){}
         fun shownormalLayout(){}
         fun onGenresOtained()

     }

    interface MainActivityPresenter{
        fun getUpcomingMovies()
        fun getTopRatedMovies()
        fun getPopularMovies()
        fun getGenres()
        fun searchMovie(query:String)
        fun onSearchClicked()
        fun onCancelSearchClicked()
        fun getLocalData()
         fun saveMovies(movie: ArrayList<Movie>)


    }
}


/*genero en la home,
    detalle.
    animacion scroll detalle,
    suscripcion,/
 */