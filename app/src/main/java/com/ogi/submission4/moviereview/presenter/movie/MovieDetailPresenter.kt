package com.ogi.submission4.moviereview.presenter.movie

import android.content.Context
import com.ogi.submission4.moviereview.model.database.MovieReviewDB
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.view.OptionsFavoriteView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDetailPresenter(var context: Context) {
    private val db = MovieReviewDB.getDatabase(context)
    val view: OptionsFavoriteView = context as OptionsFavoriteView

    fun addToFavorite(movie: MovieData){
        GlobalScope.launch(Dispatchers.Main) {
            db.movieDao().insert(movie)
            view.onFavorited()
        }
    }

    fun removeFromFavorite(movie: MovieData){
        GlobalScope.launch(Dispatchers.Main) {
            db.movieDao().delete(movie)
            view.onUnFavorited()
        }
    }

    @Suppress("SENSELESS_COMPARISON")
    fun isFavorited(movie: MovieData): Boolean{
        return db.movieDao().getById(movie.id) != null
    }
}