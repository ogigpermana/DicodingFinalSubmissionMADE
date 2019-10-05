package com.ogi.submission4.moviereview.presenter.movie

import android.content.Context
import com.google.gson.Gson
import com.ogi.submission4.moviereview.model.database.MovieReviewDB
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.model.movie.MovieResponse
import com.ogi.submission4.moviereview.network.api.TMDbApi
import com.ogi.submission4.moviereview.network.repository.MovieReviewRepository
import com.ogi.submission4.moviereview.utils.Network
import com.ogi.submission4.moviereview.view.MovieView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviePresenter(

    val context: Context,
    private val view: MovieView,
    private val repository: MovieReviewRepository = MovieReviewRepository(),
    private val gson: Gson = Gson(),
    private var sourceType: String = "main"

    ) {
    private val database = MovieReviewDB.getDatabase(context)

    fun getMovieList(lang: String, query: String? = null){
        view.showLoading()
        var source = TMDbApi.getPopularMovies(lang)
        if (sourceType == "search") {
            source = TMDbApi.searchMovies(lang, query)
        }
            if (Network.isInternetAvailable(context))
                GlobalScope.launch(Dispatchers.Main) {
                val data = gson.fromJson(
                    repository.doRequestAsync(source).await(), MovieResponse::class.java
                )
                if (data.results.isNotEmpty()){
                    view.loadMovie(data.results)
                }else {
                    view.movieNotFound()
                }
                view.hideLoading()
            }
            else{
                view.noInternetConnection()
                view.hideLoading()
            }
        }

    fun getFavoriteMovieList():List<MovieData>{
        return database.movieDao().all
    }

    fun getFavoriteMovie(){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = database.movieDao().all
            if (data.isNotEmpty()){
                view.loadMovie(data)
            }else{
                view.movieNotFound()
            }
            view.hideLoading()
        }
    }
}