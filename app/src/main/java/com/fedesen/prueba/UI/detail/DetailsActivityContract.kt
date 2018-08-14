package com.fedesen.prueba.UI.detail

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import com.fedesen.prueba.model.Genre
import com.fedesen.prueba.model.Movie


class DetailsActivityContract {


     interface DetailsActivityView{

         fun onPaletteObtained(palette:Palette.Swatch?){}

         fun onMovieAdded(){}

         fun onMovieRemoved(){}
          fun onMovieStatusObtained(CHANGE_ON: Int)

         fun onYearObtainied(year:String)




     }

    interface DetailsActivityPresenter{

        fun loadPalette(bitmap:Bitmap){}
         fun onSuscribeButtonClicked(movie: Movie)
         fun movieStatus(movie: Movie)
         fun movieDate(movie: Movie)


    }
}
