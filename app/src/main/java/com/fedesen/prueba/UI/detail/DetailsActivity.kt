package com.fedesen.prueba.UI.detail

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.transition.Slide
import android.view.Gravity
import android.view.View
import com.fedesen.prueba.App
import com.fedesen.prueba.R
import com.fedesen.prueba.model.Movie
import com.github.florent37.expectanim.ExpectAnim
import com.github.florent37.expectanim.core.Expectations.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*


class DetailsActivity : AppCompatActivity(),DetailsActivityContract.DetailsActivityView {


    private val presenter: DetailsActivityPreseter = DetailsActivityPreseter(sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getContext()), view = this)
    private var expectAnimMove: ExpectAnim? = null

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initializeViews()
        setupWindowAnimations()

    }


    override fun onYearObtainied(year: String) {
        movieYear.text = year

    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }


    private fun initializeViews() {

        movie = intent.getSerializableExtra("movie") as Movie

        movieNameDetail.text = movie.originalTitle

        // this method help to retrieve the movie initial suscriptionStatus//
        presenter.movieStatus(movie)

        presenter.movieDate(movie)

        back_button.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

        }

        suscribeButton.setOnClickListener {
            presenter.onSuscribeButtonClicked(movie)
        }

        moviewoverview.text  = movie.overview

        Picasso.with(App.getContext())
                .load(movie.coverUrl)
                .into(movieImageDetails,  object : Callback {
                    override fun onSuccess() {
                        presenter.loadPalette((movieImageDetails.getDrawable() as BitmapDrawable).bitmap)
                    }

                    override fun onError() {
                        // empty method. (looks ugly)
                    }
                })




        nestedSrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
            v, scrollX, scrollY, oldScrollX, oldScrollY ->

            val percent = scrollY * 1.5f / v.maxScrollAmount

            this.expectAnimMove?.setPercent(percent)
        })



        initAnimation()


    }

    override fun onMovieStatusObtained(CHANGE: Int) {

        suscribeButton.visibility =View.VISIBLE
        // se suscribio
        if(CHANGE==0){
            suscribeButton.text = resources.getText(R.string.subscribe_on)
            suscribeButton.background = resources.getDrawable(R.drawable.shape_subscribe_on)
            suscribeButton.setTextColor(Color.BLACK)
        }
        if(CHANGE==1){
            suscribeButton.text = resources.getText(R.string.subscribe_off)
            suscribeButton.background = resources.getDrawable(R.drawable.shape_subscribe_off)
            suscribeButton.setTextColor(Color.WHITE)


        }
    }


    private fun initAnimation() {

        expectAnimMove =  ExpectAnim()

                .expect(avatar)
                .toBe(
                        topOfParent().withMarginDp(20f),
                        scale(0.2f, 0.2f)
                )

                .expect(movieYear)
                .toBe(
                        belowOf(movieNameDetail),
                        alpha(0f)
                )

                .expect(movieNameDetail)
                .toBe(
                        belowOf(avatar),
                        alpha(0f)
                )

                .expect(suscribeButton)
                .toBe(
                        belowOf(movieYear),
                        alpha(0.2f)
                )

                .expect(background)
                .toBe(
                        height(200).withGravity(Gravity.LEFT, Gravity.TOP)
                )

                .toAnimation()


    }

    override fun onPaletteObtained(palette: Palette.Swatch?) {
        super.onPaletteObtained(palette)


        Picasso.with(App.getContext())
                .load(movie.backurl)
                .fit()
                .centerCrop()
                .into(backdropImage ,object: Callback {
                    override fun onSuccess() {
                        alphaImage.setBackgroundColor(palette!!.rgb)
                        overViewTitle.setTextColor(palette!!.titleTextColor)
                        alphaImage.alpha = 0.6f
                    }

                    override fun onError() {
                        // empty method. (looks ugly)
                    }
                })
    }

    private fun setupWindowAnimations() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slide = Slide()
            slide.duration = 1000
            window.exitTransition = slide

        }
    }



}


