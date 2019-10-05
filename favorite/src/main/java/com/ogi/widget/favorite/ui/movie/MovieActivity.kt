package com.ogi.widget.favorite.ui.movie

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.ogi.widget.favorite.R
import com.ogi.widget.favorite.adapter.MovieAdapter
import com.ogi.widget.favorite.model.MovieData
import com.ogi.widget.favorite.utils.FavoriteConst.AUTHORITY_MOVIE
import com.ogi.widget.favorite.utils.FavoriteConst.FLAG_MOVIE
import com.ogi.widget.favorite.utils.FavoriteConst.TABLE_MOVIE
import com.ogi.widget.favorite.utils.FavoriteCursorHelper
import com.ogi.widget.favorite.utils.hide
import com.ogi.widget.favorite.utils.show
import kotlinx.android.synthetic.main.activity_movie.*
import org.jetbrains.anko.startActivity

@Suppress("DEPRECATION")
class MovieActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    private val mMovieData: MutableList<MovieData> = mutableListOf()
    private lateinit var adapter: MovieAdapter

    private val contentUri = Uri.Builder()
        .scheme("content")
        .authority(AUTHORITY_MOVIE)
        .appendPath(TABLE_MOVIE)
        .build()

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        progress.show()
        return CursorLoader(this, contentUri, null, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        mMovieData.clear()
        mMovieData.addAll(FavoriteCursorHelper.favCursorToListMovie(cursor))
        adapter.notifyDataSetChanged()
        tvDataNotFound.hide()
        progress.hide()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mMovieData.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        supportLoaderManager.restartLoader(100, null, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val actionBar = supportActionBar
        actionBar?.title = "Movies"

        adapter = MovieAdapter(this, mMovieData){
            startActivity<MovieDetailActivity>(FLAG_MOVIE to it)
        }

        rv_movies.layoutManager = LinearLayoutManager(this)
        rv_movies.adapter = adapter

        supportLoaderManager.initLoader(100, null, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            this.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
