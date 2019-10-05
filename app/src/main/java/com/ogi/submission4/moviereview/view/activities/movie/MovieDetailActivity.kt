package com.ogi.submission4.moviereview.view.activities.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.presenter.movie.MovieDetailPresenter
import com.ogi.submission4.moviereview.utils.MovieReviewConst
import com.ogi.submission4.moviereview.utils.MovieReviewConst.FLAG_MOVIE
import com.ogi.submission4.moviereview.utils.getYear
import com.ogi.submission4.moviereview.view.OptionsFavoriteView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_moive_detail.*
import org.jetbrains.anko.design.snackbar

class MovieDetailActivity : AppCompatActivity(), OptionsFavoriteView {
    private lateinit var movie: MovieData
    private lateinit var presenter: MovieDetailPresenter
    private var menuItem: Menu? = null

    override fun onFavorited() {
        favoriteState()
        scrollView.snackbar(getString(R.string.added_to_favorite)).show()
    }

    override fun onUnFavorited() {
        favoriteState()
        scrollView.snackbar(getString(R.string.removed_from_favorite)).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moive_detail)

        movie = intent.getParcelableExtra(FLAG_MOVIE)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = movie.originalTitle
        }

        Picasso.get()
            .load(MovieReviewConst.IMG_URL + movie.backdropPath)
            .placeholder(R.drawable.backdrop_path)
            .error(R.drawable.broken_image)
            .into(iv_backdrop_path)
        Picasso.get()
            .load(MovieReviewConst.IMG_URL + movie.posterPath)
            .placeholder(R.drawable.default_poster_path)
            .error(R.drawable.broken_image)
            .into(iv_poster_path)
        tv_single_title.text = movie.originalTitle
        tv_single_release.text = movie.releaseDate?.getYear()
        rb_single_rating.rating = movie.voteAverage/2
        tv_single_overview.text = movie.overview
        tv_single_rating.text = movie.voteAverage.toString()

        presenter = MovieDetailPresenter(this)
        favoriteState()
    }

    private fun favoriteState() {
        if (presenter.isFavorited(movie))
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        favoriteState()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (presenter.isFavorited(movie))
                    presenter.removeFromFavorite(movie)
                else
                    presenter.addToFavorite(movie)
                favoriteState()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
