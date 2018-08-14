package com.fedesen.prueba.adapter

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
import com.fedesen.prueba.UI.home.MainActivityContract


class AdapterMovieSuscription(
        private val view : MainActivityContract.MainActivityViewInterface,
        private val activity: AppCompatActivity,
        val mDataSet: ArrayList<Movie>,
        private val mLayout: Int

) : RecyclerView.Adapter<AdapterMovieSuscription.MovieHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(mLayout, parent, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = mDataSet[position]
        setCover(holder, movie)
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
        holder.cover.setOnClickListener {
           view.navigateDetails(movie)

        }
    }


    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cover: ImageView


        init {
            cover = v.findViewById(R.id.movieImageSuscription) as ImageView

        }
    }




}
