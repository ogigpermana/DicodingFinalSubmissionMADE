package com.ogi.submission4.moviereview.view

import com.ogi.submission4.moviereview.model.tv.entity.TvData

interface TvShowView {
    fun showLoading()
    fun hideLoading()
    fun loadTvShow(data: List<TvData>)
    fun tvNotFound()
    fun noInternetConnection()
}