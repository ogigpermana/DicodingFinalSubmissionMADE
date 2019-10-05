package com.ogi.submission4.moviereview.view.widgets

import android.content.Context
import com.ogi.submission4.moviereview.model.database.MovieReviewDB
import com.ogi.submission4.moviereview.model.movie.entity.MovieData

class MovieReviewWidgetPresenter(var context: Context) {
    val database = MovieReviewDB.getDatabase(context)

    fun getListFavoriteMovies(): List<MovieData>{
        return database.movieDao().all
    }
}