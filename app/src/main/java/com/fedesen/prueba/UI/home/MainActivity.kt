package com.fedesen.prueba.UI.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.transition.Slide
import android.view.View
import android.widget.Toast
import com.fedesen.prueba.App
import com.fedesen.prueba.R
import com.fedesen.prueba.UI.detail.DetailsActivity
import com.fedesen.prueba.adapter.AdapterMovie
import com.fedesen.prueba.adapter.AdapterMovieSuscription
import com.fedesen.prueba.adapter.SearchAdapter
import com.fedesen.prueba.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import android.app.ActivityOptions




class MainActivity : AppCompatActivity(), MainActivityContract.MainActivityViewInterface {


    private  var sharedPreferences:SharedPreferences =  PreferenceManager.getDefaultSharedPreferences(App.getContext())
    private val preseter: MainActivityPreseter = MainActivityPreseter(this, sharedPreferences = sharedPreferences)
    private var adapter : AdapterMovie? = null
    private var searchAdapter : SearchAdapter? = null
    private var adapterMovieSuscription : AdapterMovieSuscription? = null
    private val layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
    private val searchLayoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
    private val horizontal_manager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false) as RecyclerView.LayoutManager
    private var current_page = 1
    val DETAILS_BACK_CODE = 666


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWindowAnimations()
        initLists()
        initViews()
        preseter.getGenres()

    }

    override fun onGenresOtained() {
        preseter.getMovies(current_page)
        preseter.getSuscribedMovies()
    }

    override fun onSearhObtainedMovies(movies: ArrayList<Movie>) {
        super.onSearhObtainedMovies(movies)
        searchAdapter = SearchAdapter(this,this, sharedPreferences, movies, R.layout.movie_cell_search)
        searchList.adapter = searchAdapter
        searchList.visibility= View.VISIBLE

    }

    override fun onSubscribedMoviesObtained(movie: ArrayList<Movie>) {
        super.onSubscribedMoviesObtained(movie)
        adapterMovieSuscription = AdapterMovieSuscription(this, this, movie, R.layout.movie_cell_suscription)
        suscriptionList.adapter = adapterMovieSuscription
        sus_Layout.visibility = View.VISIBLE
        if(movie.size==0)sus_Layout.visibility = View.GONE

    }

    override fun onMoviesObtained(movie: ArrayList<Movie> ,page_number:Int) {
        super.onMoviesObtained(movie,page_number)
        if(adapter==null) adapter = AdapterMovie(this, this, movie, R.layout.movie_cell)
        movieList.adapter = adapter

        //pagintion//
        if(current_page!=1){
            adapter!!.mDataSet.addAll(movie)
            adapter!!.notifyDataSetChanged()
        }
        current_page ++
        recommendedLayout.visibility = View.VISIBLE

        preseter.getSuscribedMovies()


    }

    override fun onError(s: String) {
        Toast.makeText(this@MainActivity,s,Toast.LENGTH_SHORT).show()
    }

    override fun setLoaderVisiblity(visibility: Int) {
        super.setLoaderVisiblity(visibility)
        progresBar.visibility = visibility
    }

    @SuppressLint("RestrictedApi")
    override fun navigateDetails(movie: Movie) {

        super.navigateDetails(movie)
        val options = ActivityOptions.makeCustomAnimation(this,R.anim.push_left_in,
                R.anim.push_left_out)
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra("movie", movie)
        startActivityForResult(intent,DETAILS_BACK_CODE,options.toBundle())


     }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == DETAILS_BACK_CODE){
            preseter.getSuscribedMovies()
            if(searchList.visibility == View.VISIBLE){
                preseter.searchMovie(searchEditText.text.toString())
            }
        }
    }

    private fun initLists() {

        //main list//
        movieList.layoutManager = layoutManager
        movieList.itemAnimator = DefaultItemAnimator()
        scroller.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                preseter.getMovies(current_page)
            }
        })

        //suscriptionList
        suscriptionList.layoutManager = horizontal_manager
        searchList.layoutManager = searchLayoutManager

    }

    override fun showSearchLayout() {
        super.showSearchLayout()
        normalLayout.visibility = View.GONE
        searchLayout.visibility = View.VISIBLE
        searchEditText.requestFocus()

    }

    override fun shownormalLayout() {
        super.shownormalLayout()
        normalLayout.visibility = View.VISIBLE
        searchLayout.visibility = View.GONE
        searchEditText.text.clear()
        searchList.visibility= View.GONE
    }

    private fun setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slide = Slide()
            slide.duration = 1000
            window.enterTransition = slide
        }
    }

    override fun onBackPressed() {


        if(searchLayout.visibility!=View.VISIBLE){
            super.onBackPressed()

        }else{
            preseter.onCancelSearchClicked()
            preseter.getSuscribedMovies()
        }
    }

    private fun initViews() {
        serchButton.setOnClickListener {
            preseter.onSearchClicked()
        }

        cancelSearchButton.setOnClickListener {
            preseter.onCancelSearchClicked()
            preseter.getSuscribedMovies()
        }

        searchEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length != 0) preseter.searchMovie(s.toString())
            }
        })


    }





}
