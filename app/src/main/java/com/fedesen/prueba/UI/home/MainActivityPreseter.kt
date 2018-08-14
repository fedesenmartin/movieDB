package com.fedesen.prueba.UI.home


import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.fedesen.prueba.SharedPrefercesHelper
import com.fedesen.prueba.api.ListMoviesResponseWrapper
import com.fedesen.prueba.api.MovieService
import com.fedesen.prueba.api.RetrofitManager
import com.fedesen.prueba.api.SearchMoviesResponseWrapper
import com.fedesen.prueba.model.Genre
import com.fedesen.prueba.model.GenreResponse
import com.fedesen.prueba.model.Movie
import retrofit2.Call
import retrofit2.Callback

class MainActivityPreseter(private val view : MainActivityContract.MainActivityViewInterface,
                           private val sharedPreferences:SharedPreferences
) : MainActivityContract.MainActivityPresenter {


    val  movieService: MovieService = RetrofitManager().getInstance().create(MovieService::class.java)

    override fun getMovies(page_number:Int) {

            view.setLoaderVisiblity(View.VISIBLE)
            movieService.getMovies(page_number).enqueue(object : Callback<ListMoviesResponseWrapper> {
                override fun onResponse(call: Call<ListMoviesResponseWrapper>?, response: retrofit2.Response<ListMoviesResponseWrapper>?) {
                    view.setLoaderVisiblity(View.INVISIBLE)
                    // here I parse data obtained from server,and set the genre
                    // for the purpose of ejercice I only set only one genre for movie
                        for(movie in response!!.body()!!.results){
                            parseMovieGenre(movie)
                        }
                        view.onMoviesObtained(response!!.body()!!.results, response!!.body()!!.page?.toInt()!!)

                }

                override fun onFailure(call: Call<ListMoviesResponseWrapper>?, t: Throwable?) {
                    view.setLoaderVisiblity(View.INVISIBLE)
                    t?.printStackTrace()
                    view.onError(t!!.localizedMessage)
                }
            })


    }

    override fun searchMovie(query: String) {
        view.setLoaderVisiblity(View.VISIBLE)
        movieService.searchMovies(query).enqueue(object : Callback<SearchMoviesResponseWrapper> {
            override fun onResponse(call: Call<SearchMoviesResponseWrapper>?, response: retrofit2.Response<SearchMoviesResponseWrapper>?) {
                view.setLoaderVisiblity(View.INVISIBLE)
                // here I parse data obtained from server,and set the genre
                // for the purpose of ejercice I only set only one genre for movie
                for(movie in response!!.body()!!.results){
                    parseMovieGenre(movie)
                }
                view.onSearhObtainedMovies(response!!.body()!!.results)

            }

            override fun onFailure(call: Call<SearchMoviesResponseWrapper>?, t: Throwable?) {
                view.setLoaderVisiblity(View.INVISIBLE)
                t?.printStackTrace()
                view.onError(t!!.localizedMessage)
            }
        })


    }

    override fun getGenres() {
        view.setLoaderVisiblity(View.VISIBLE)
        movieService.getGenre().enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>?, response: retrofit2.Response<GenreResponse>?) {

                //save it locally and get the movies//
                SharedPrefercesHelper.saveGenre(sharedPreferences.edit(),response!!.body()!!.genres)
                view.onGenresOtained()
                view.setLoaderVisiblity(View.INVISIBLE)

            }

            override fun onFailure(call: Call<GenreResponse>?, t: Throwable?) {

                view.onError(t!!.localizedMessage)
                view.setLoaderVisiblity(View.INVISIBLE)

            }

        })
    }

    //only one genre for each movie//
    private fun parseMovieGenre(movie: Movie) {
        var genresList = SharedPrefercesHelper.getGenre(sharedPreferences)
            Log.d("PELICULA", movie.originalTitle)
            if(movie.genreIds.isEmpty()){
                movie.genre = Genre(0,"Uknown")
            }else{
                movie.genre = getgendreById(movie.genreIds[0],genresList)

            }


    }

    private fun getgendreById(l: Long, genresList: List<Genre>?): Genre? {
        if (genresList != null) {
            for (g in genresList){
                if(g.id == l.toInt()){
                    return  g
                }
            }
        }
        return  Genre(0,"-")

    }

    override fun onSearchClicked() {

        view.showSearchLayout()
    }

    override fun onCancelSearchClicked() {
        view.shownormalLayout()
    }

    override fun getSuscribedMovies() {
        val movies:ArrayList<Movie>?=SharedPrefercesHelper.getMovies()

        if(movies!=null){
            view.onSubscribedMoviesObtained(movies)
        }
    }


}