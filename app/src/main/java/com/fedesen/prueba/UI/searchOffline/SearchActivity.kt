package com.fedesen.prueba.UI.searchOffline

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Slide
import android.view.View
import com.fedesen.prueba.R
import com.fedesen.prueba.UI.detail.DetailsActivity
import com.fedesen.prueba.adapter.AdapterMovie
import com.fedesen.prueba.model.Genre
import com.fedesen.prueba.adapter.SearchAdapter
import com.fedesen.prueba.adapter.SearchAdapterOffline
import com.fedesen.prueba.model.Movie
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() ,SearchActivityContract.SearchActivityViewInterface{


    lateinit var movieList:ArrayList<Movie>
    lateinit var genreList:ArrayList<Genre>
    private val preseter: SearchActivityPresenter = SearchActivityPresenter(this)
    private var searchAdapter : SearchAdapterOffline? = null
    private val searchManager = LinearLayoutManager(this) as RecyclerView.LayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if(this.intent.extras!=null){
            movieList = this.intent.extras.getSerializable("movie") as ArrayList<Movie>
            genreList = this.intent.extras.getSerializable("genres") as ArrayList<Genre>
            preseter.getGenresTextList(genreList)
            initViews()
        }


    }

    override fun onMoviesObtained(movie: ArrayList<com.fedesen.prueba.model.Movie>) {
        super.onMoviesObtained(movie)
        searchAdapter = SearchAdapterOffline(this, movie, R.layout.movie_cell_search)
        searchListOffline.adapter = searchAdapter
        searchListOffline.visibility = View.VISIBLE
    }

    private fun initViews() {
        setupWindowAnimations()

        spinner.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<String> {
            view, position, id, item ->
            preseter.getMoviesForGender(genreList[position],movieList)

        })
        searchListOffline.layoutManager = searchManager

    }


    @SuppressLint("RestrictedApi")
    override fun navigateDetails(movie: Movie) {
        super.navigateDetails(movie)
        val options = ActivityOptions.makeCustomAnimation(this,R.anim.push_left_in,
                R.anim.push_left_out)
        val intent = Intent(this@SearchActivity, DetailsActivity::class.java)
        intent.putExtra("movie",movie)
        startActivity(intent,options.toBundle())

    }

    override fun showLoading(visible: Int) {
        progresBarSearch.visibility = visible
    }
    override fun onGenreListTextObtained(b: MutableList<String>) {
        spinner.setItems(b)
        preseter.getMoviesForGender(genreList[0],movieList)

    }

    private fun setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slide = Slide()
            slide.duration = 1000
            window.exitTransition = slide

        }
    }
}
