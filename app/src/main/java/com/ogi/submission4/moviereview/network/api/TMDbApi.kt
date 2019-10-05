package com.ogi.submission4.moviereview.network.api

import com.ogi.submission4.moviereview.BuildConfig
import com.ogi.submission4.moviereview.utils.MovieReviewConst

object TMDbApi {
    private const val ENDPOINT = MovieReviewConst.BASE_URL
    private const val API_KEY = BuildConfig.TMDB_API_KEY

    fun getPopularMovies(Language: String = "en-US") = "${ENDPOINT}movie/popular?api_key=${API_KEY}&language=${Language}"
    fun getPopularTvShows(Language: String = "en-US") = "${ENDPOINT}tv/popular?api_key=${API_KEY}&language=${Language}"
    fun searchMovies(Language: String, query: String?) = "${ENDPOINT}search/movie?api_key=${API_KEY}&language=${Language}&query=${query}"
    fun searchTvShow(Language: String, query: String?) = "${ENDPOINT}search/tv?api_key=${API_KEY}&language=${Language}&query=${query}"
    fun getNewRelease(today: String) = "${ENDPOINT}discover/movie?api_key=$API_KEY&primary_release_date.gte=$today&primary_release_date.lte=$today"
}