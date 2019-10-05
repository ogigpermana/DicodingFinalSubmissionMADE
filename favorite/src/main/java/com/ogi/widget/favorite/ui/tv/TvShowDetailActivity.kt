package com.ogi.widget.favorite.ui.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ogi.widget.favorite.R
import com.ogi.widget.favorite.model.TvData
import com.ogi.widget.favorite.utils.FavoriteConst.IMAGE_BASE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var tvshow: TvData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val actionBar = supportActionBar
        tvshow = intent.getParcelableExtra("tvshow")
        actionBar?.title = tvshow.originalName

        tv_single_title.text = tvshow.originalName
        tv_single_rating.text = tvshow.voteAverage.toString()
        rb_single_rating.rating = tvshow.voteAverage
        tv_single_overview.text = tvshow.overview
        tv_single_release.text = tvshow.releaseDate
        Picasso
            .get()
            .load(IMAGE_BASE_URL+tvshow.posterPath)
            .placeholder(R.drawable.default_poster_path)
            .error(R.drawable.broken_image)
            .into(iv_poster_path)
        Picasso
            .get()
            .load(IMAGE_BASE_URL+tvshow.backdropPath)
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
