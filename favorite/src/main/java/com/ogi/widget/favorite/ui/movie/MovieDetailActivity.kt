package com.ogi.widget.favorite.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ogi.widget.favorite.R
import com.ogi.widget.favorite.model.MovieData
import com.ogi.widget.favorite.utils.FavoriteConst.IMAGE_BASE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movie: MovieData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val actionBar = supportActionBar
        movie = intent.getParcelableExtra("movie")
        actionBar?.title = movie.originalTitle

        tv_single_title.text = movie.originalTitle
        tv_single_release.text = movie.releaseDate
        rb_single_rating.rating = movie.voteAverage/2
        tv_single_overview.text = movie.overview
        Picasso
            .get()
            .load(IMAGE_BASE_URL+movie.posterPath)
            .placeholder(R.drawable.default_poster_path)
            .error(R.drawable.broken_image)
            .into(iv_poster_path)
        Picasso
            .get()
            .load(IMAGE_BASE_URL+movie.backdropPath)
            .placeholder(R.drawable.backdrop_path)
            .error(R.drawable.broken_image)
            .into(iv_backdrop_path)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            this.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
