package com.ogi.submission4.moviereview.view.activities.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.tv.entity.TvData
import com.ogi.submission4.moviereview.presenter.tv.TvShowDetailPresenter
import com.ogi.submission4.moviereview.utils.MovieReviewConst
import com.ogi.submission4.moviereview.utils.MovieReviewConst.FLAG_TV
import com.ogi.submission4.moviereview.view.OptionsFavoriteView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import org.jetbrains.anko.design.snackbar

class TvShowDetailActivity : AppCompatActivity(), OptionsFavoriteView {

    private lateinit var tvshow: TvData
    private lateinit var presenter: TvShowDetailPresenter
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
        setContentView(R.layout.activity_tv_show_detail)

        tvshow = intent.getParcelableExtra(FLAG_TV)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = tvshow.originalName
        }

        Picasso.get()
            .load(MovieReviewConst.IMG_URL + tvshow.backdropPath)
            .placeholder(R.drawable.backdrop_path)
            .error(R.drawable.broken_image)
            .into(iv_backdrop_path)
        Picasso.get()
            .load(MovieReviewConst.IMG_URL + tvshow.posterPath)
            .placeholder(R.drawable.default_poster_path)
            .error(R.drawable.broken_image)
            .into(iv_poster_path)
        tv_single_title.text = tvshow.originalName
        tv_single_release.text = tvshow.releaseDate
        rb_single_rating.rating = tvshow.voteAverage/2
        tv_single_overview.text = tvshow.overview
        tv_single_rating.text = tvshow.voteAverage.toString()

        presenter = TvShowDetailPresenter(this)
        favoriteState()
    }

    private fun favoriteState() {
        if (presenter.isFavorited(tvshow))
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
                if (presenter.isFavorited(tvshow))
                    presenter.removeFromFavorite(tvshow)
                else
                    presenter.addToFavorite(tvshow)
                favoriteState()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
