package com.fedesen.prueba.UI.detail


import android.content.SharedPreferences
import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.widget.Toast
import com.fedesen.prueba.App
import com.fedesen.prueba.Constants
import com.fedesen.prueba.data.SharedPrefercesHelper
import com.fedesen.prueba.model.Movie
import java.util.*

class DetailsActivityPreseter(private  val sharedPreferences: SharedPreferences,private val view : DetailsActivityContract.DetailsActivityView
) : DetailsActivityContract.DetailsActivityPresenter {

    override fun onSuscribeButtonClicked(movie: Movie) {


        //si esta suscripto lo remuevo
        var change = -1
        if(SharedPrefercesHelper.isAvailable(movie.id?.toInt(),sharedPreferences)){
            SharedPrefercesHelper.removeMovie(sharedPreferences,movie)
            change = Constants.CHANGE_OFF
        }else{
            SharedPrefercesHelper.saveMovie(sharedPreferences,movie)
            change = Constants.CHANGE_ON


        }
        view.onMovieStatusObtained(change)


    }

    override fun movieDate(movie: Movie) {
        val cal = Calendar.getInstance()
        cal.setTime(movie.releaseDate)
        val year = cal.get(Calendar.YEAR)
        view.onYearObtainied(year.toString())

    }

    override fun movieStatus(movie: Movie) {
        if( SharedPrefercesHelper.isAvailable(movie.id?.toInt(),sharedPreferences) ){

            view.onMovieStatusObtained(Constants.CHANGE_ON)
        }else{
            view.onMovieStatusObtained(Constants.CHANGE_OFF)

        }


    }

    override fun loadPalette(bitmap: Bitmap) {
        super.loadPalette(bitmap)

            if (bitmap != null) {
                Palette.from(bitmap)
                        .generate(Palette.PaletteAsyncListener { palette ->
                            val textSwatch = palette.dominantSwatch
                            if (textSwatch == null) {
                                Toast.makeText(App.getContext(), "Null swatch :(", Toast.LENGTH_SHORT).show()
                                return@PaletteAsyncListener
                            }

                            view.onPaletteObtained(textSwatch)

                        })
            }
    }

}