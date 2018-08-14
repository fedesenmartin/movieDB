package com.fedesen.prueba

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fedesen.prueba.model.Genre
import com.fedesen.prueba.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


// This class help  to save object to shared preferences//
class SharedPrefercesHelper{

    companion object {

        fun saveGenre(prefsEditor:SharedPreferences.Editor,genre: List<Genre>) {
            val gson = Gson()
            val jsonText = gson.toJson(genre)
            prefsEditor.putString("genres", jsonText)
            prefsEditor.apply()
        }

        fun getGenre(prefs:SharedPreferences): List<Genre>? {
            val gson = Gson()
            val jsonText = prefs.getString("genres", null)
            return gson.fromJson(jsonText, object : TypeToken<List<Genre>>() {}.type)
        }

        fun saveMovie(prefsEditor:SharedPreferences, movie: ArrayList<Movie>) {
            val gson = Gson()
            val jsonText = gson.toJson(movie)
            prefsEditor.edit().putString("movies", jsonText).apply()
        }

        fun saveMovie(prefsEditor:SharedPreferences, movie: MutableList<Movie>) {
            val gson = Gson()
            val jsonText = gson.toJson(movie)
            prefsEditor.edit().putString("movies", jsonText).apply()
        }

        fun getMovies(): ArrayList<Movie>? {
            val gson = Gson()
            var jsontext =  PreferenceManager.getDefaultSharedPreferences(App.getContext()).getString("movies",null)
            return gson.fromJson(jsontext, object : TypeToken<List<Movie>>() {}.type)
        }

        fun isAvailable(id: Int?, sharedPreferences: SharedPreferences):Boolean{
            var  list = getMovies()
            if(list==null)return false
            for(m in list!!) {
                if (m.id?.toInt() == id){
                    return true
                }
            }
            return false
        }

        fun saveMovie(prefs: SharedPreferences, movie:Movie) {
            var selectedSeries = getMovies()?.toMutableList()
            if(selectedSeries==null){
                var newlist = ArrayList<Movie>()
                newlist.add(movie)
                saveMovie(prefs, newlist)
            }else{
                selectedSeries?.add(movie)
                saveMovie(prefs, selectedSeries!!)
            }

        }

        fun removeMovie(sharedPreferences: SharedPreferences, movie: Movie) {
            val selectedSeries = getMovies()?.toMutableList()
            val iter = selectedSeries?.iterator()
            while (iter?.hasNext()!!) {
                val m = iter.next()
                if (m.id==movie.id){
                    iter.remove()
                }
            }
            saveMovie(sharedPreferences, selectedSeries)

        }
    }
}