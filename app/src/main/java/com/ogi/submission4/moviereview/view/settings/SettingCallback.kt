package com.ogi.submission4.moviereview.view.settings

import android.content.Context
import com.ogi.submission4.moviereview.model.movie.entity.MovieData

interface SettingCallback {
    fun getNewReleaseMovieList(context: Context, movies: List<MovieData>)
}