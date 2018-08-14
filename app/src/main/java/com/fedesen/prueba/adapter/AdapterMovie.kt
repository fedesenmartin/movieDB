package com.fedesen.prueba.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fedesen.prueba.App
import com.fedesen.prueba.R
import com.fedesen.prueba.model.Movie
import com.squareup.picasso.Picasso
import android.graphics.ColorMatrixColorFilter
import android.graphics.ColorMatrix
import android.support.v7.app.AppCompatActivity
import com.fedesen.prueba.UI.home.MainActivityContract


class AdapterMovie(
        private val view : MainActivityContract.MainActivityViewInterface,
        private val activity: AppCompatActivity,
        val mDataSet: ArrayList<Movie>,
        private val mLayout: Int

) : RecyclerView.Adapter<AdapterMovie.MovieHolder>() {

    private val matrix: ColorMatrix = ColorMatrix()

    val filter = ColorMatrixColorFilter(matrix)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(mLayout, parent, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = mDataSet[position]
        setCover(holder, movie)
        setTitle(holder, movie)
        onCoverClicked(holder, movie)

    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    private fun setTitle(holder: MovieHolder, movie: Movie) {
        holder.name.text = movie.originalTitle
    }

    private fun setCover(holder: MovieHolder, movie: Movie) {
        matrix.setSaturation(0f)

        Picasso.with(App.getContext())
                .load(movie.coverUrl)
                .fit()
                .centerCrop()
                .into(holder.cover)
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)

        val filter = ColorMatrixColorFilter(matrix)
        holder.cover.setColorFilter(filter)
        holder.genre.text = movie.genre!!.name

    }

    private fun onCoverClicked(holder: MovieHolder, movie: Movie) {
        holder.cover.setOnClickListener {
            view.navigateDetails(movie)

        }
    }


    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cover: ImageView
        val name: TextView
        val genre :TextView

        init {
            cover = v.findViewById(R.id.movieImage) as ImageView
            name = v.findViewById(R.id.movieName) as TextView
            genre = v.findViewById(R.id.genreText) as TextView
        }
    }




}
