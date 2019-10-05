package com.ogi.submission4.moviereview.view

import com.ogi.submission4.moviereview.model.movie.entity.MovieData

interface MovieView {
    fun showLoading()
    fun hideLoading()
    fun loadMovie(data: List<MovieData>)
    fun movieNotFound()
    fun noInternetConnection()
}