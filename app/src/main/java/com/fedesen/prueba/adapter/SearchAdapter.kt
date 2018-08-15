package com.fedesen.prueba.adapter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.fedesen.prueba.App
import com.fedesen.prueba.R
import com.fedesen.prueba.model.Movie
import com.squareup.picasso.Picasso
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.fedesen.prueba.SharedPrefercesHelper
import com.fedesen.prueba.UI.home.MainActivityContract


class SearchAdapter(
        private val view : MainActivityContract.MainActivityViewInterface,
        val mDataSet: ArrayList<Movie>,
        private val mLayout: Int

) : RecyclerView.Adapter<SearchAdapter.MovieHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(mLayout, parent, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = mDataSet[position]
        setCover(holder, movie)
        holder.name.text = movie.originalTitle
        holder.genre.text = movie.genre!!.name
        onCoverClicked(holder, movie)

    }




    override fun getItemCount(): Int {
        return mDataSet.size
    }



    private fun setCover(holder: MovieHolder, movie: Movie) {

        Picasso.with(App.getContext())
                .load(movie.coverUrl)
                .fit()
                .centerCrop()
                .into(holder.cover)


    }


    private fun onCoverClicked(holder: MovieHolder, movie: Movie) {
        holder.itemView.setOnClickListener {

            view.navigateDetails(movie)

        }
    }

    fun updateMovies() {
        notifyDataSetChanged()
    }


    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {
        var cover: ImageView
        var name: TextView
        var genre: TextView
        var followButton: Button





        init {
            cover = v.findViewById(R.id.movieImageSearch) as ImageView
            name = v.findViewById(R.id.moviehNameSearc) as TextView
            genre = v.findViewById(R.id.movieGenreSearch) as TextView
            followButton = v.findViewById(R.id.follow_button) as Button



        }
    }




}
